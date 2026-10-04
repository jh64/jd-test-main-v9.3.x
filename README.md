The JHipster generated README.md was moved to README.jhipster.md.

This project is for demonstrating the bug and bug fix for the code generated with generator-jhipster v9.3.0 - may be an old bug.

The bug: it incorrectly used window.location.href. And it crashed (getting a blank screen) after the "base"/"base href"/context-path (e.g., "jd-test-base") were added in the configuration files - should use React Router instead.

The bug fix can be found on this PR:
https://github.com/jhipster/generator-jhipster/pull/35007
