# Notes

Gotchas and hard-won constraints for this project - things that look like bugs but are deliberate,
non-obvious root causes, "why not the obvious approach" decisions, and big architectural choices.

**This is not a running progress log or a session diary.** Ordinary feature work that a future
session could fully understand by reading the current code gets no entry: new features, routine
fixes, renames, small additions. The test is whether a cold session would otherwise have to
re-derive or re-ask about it. When in doubt, leave it out - an entry that restates the code is
worse than no entry, because it dilutes the ones that matter and goes stale.

The "Last shipped changelog" section at the bottom is the one exception to that rule: it is a
fixed-size record, replaced wholesale each backup rather than appended to, and it is what tells the
next backup where its commit range starts. Never delete it as a progress-log violation.

## Last shipped changelog

The `changelog.md` from this project's most recent backup, verbatim - replaced wholesale each
backup, never appended to. It is a record, not a source: the next one is written fresh from the
commit range. Its purpose is that the last backup's range is knowable from this project alone,
without opening OneDrive or unpacking a zip. See copi's `BACKUP_PROTOCOL.md` for the routine.

_No backup recorded yet. Its starting point is the range line at the top of the
`changelog.md` inside this project's most recent backup folder in OneDrive._

```
26_11_11_18_24 - changes in 69a3178..fb66d6a

A fresh backup: the zip is gone from the 26_11_10_09_09 backup folder, which left the newest zip from 12 October. Nothing has changed in the mod since 26_11_10_09_09, so this zip holds the same code; the range starts at the last backup that still has its zip.

- The mod now has an issue link (Mod Menu shows it as Issues): this repository's own Issues page, github.com/scarabx/lorien-legacies-1.20.1/issues. The mod pages on Modrinth and CurseForge link there too.
```
