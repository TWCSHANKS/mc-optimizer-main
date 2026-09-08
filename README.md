# MC Optimizer

This project now builds itself — GitHub's servers compile the jar for you,
no local Java/Gradle/IDE install needed.

## Get the compiled jar (no coding, ~5 minutes)

1. Create a free GitHub account at github.com if you don't have one.
2. Click "New repository" (top right, the + icon), name it anything
   (e.g. `mc-optimizer`), leave it Public, and click Create.
3. On the empty repo page, click "uploading an existing file", then drag
   the entire unzipped `mc-optimizer-mod` folder onto the page. GitHub will
   preserve the folder structure. Commit the changes.
4. Click the "Actions" tab at the top of the repo. You should see a
   "Build mod jar" workflow running automatically (it starts on every
   upload). Wait for the yellow dot to turn into a green check — takes a
   couple of minutes the first time.
5. Click into that finished workflow run, scroll to "Artifacts" at the
   bottom, and download `mc-optimizer-jar`. It's a zip containing your
   actual `.jar` file.
6. Unzip it, and drop the `.jar` into your Minecraft `mods` folder
   (in the official launcher: `%appdata%/.minecraft/mods` on Windows,
   `~/Library/Application Support/minecraft/mods` on Mac — create the
   `mods` folder if it isn't there, and make sure you've installed
   Fabric Loader for Minecraft first from fabricmc.net/use).

That's it — launch Minecraft with the Fabric profile, and press O in-game.

If the Actions run shows a red X instead of green, click into it and open
the "Build" step — it'll show the actual compile error, which is almost
always a version-number mismatch. Paste that error back to me and I'll
fix the relevant file directly.

## If something doesn't compile

Minecraft's internal method names (via Yarn mappings) shift slightly between
versions. If `GameOptions`, `GraphicsMode`, or `ParticlesMode` show red
underlines, Ctrl+Click (Cmd+Click on Mac) into `GameOptions` from
`MinecraftClient.getInstance().options` and use autocomplete to find the
current getter/setter names for your exact version — the structure and
intent stay the same across versions, only some names move.

## Supporting 1.21 through 1.21.11

`fabric.mod.json` now declares `"minecraft": ">=1.21 <=1.21.11"`, so Fabric
Loader will let the mod load on any patch in that range. But that's a
declaration, not a guarantee -- you're compiling against one concrete
version (1.21.11, the newest, set in `gradle.properties`) and hoping
nothing you use (`GameOptions`, `GraphicsMode`, `ParticlesMode`) changed
shape between 1.21 and 1.21.11. For a small settings-screen mod like this
one, that's usually a safe bet -- Mojang rarely touches those classes in
patch releases -- but you should still launch-test on both the oldest
(1.21) and newest (1.21.11) version before publishing, since `runClient`
only tests whichever version is set in `gradle.properties` at the time.

If you later find the game options API *did* change shape somewhere in
that range and the mod breaks on some patches, the real fix is a
multi-version build tool like Stonecutter
(https://stonecutter.kikugie.dev), which compiles separate jars per
version from one codebase. Not needed here unless testing turns up an
actual break.

On Modrinth, when you upload the jar, the version-picker lets you tick
every game version it supports (1.21, 1.21.1, 1.21.2, ... 1.21.11) for
that single upload -- you don't need a separate upload per patch as long
as one jar covers the whole range.

## Next steps once it runs

- Add an icon: 128x128 PNG at `src/main/resources/assets/mcoptimizer/icon.png`.
- Update the `authors` field in `fabric.mod.json`.
- Run `./gradlew build` — the jar lands in `build/libs/mc-optimizer-1.0.0.jar`.
- Publish it on Modrinth (see the steps from earlier in this conversation).
