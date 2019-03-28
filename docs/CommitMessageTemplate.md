# Add Commit Messages Template

**Open the configuration file.**

```shell
$ vim ~/.gitconfig
```

**Add the following code.**

```
[commit]
    template = ~/.gitmessage
[core]
    editor = vim
```

**New a template file.**

```shell
$ vim ~/.gitmessage
```

**Add the following code.**

```
# head: <type>(<scope>): <subject>
# - type: feat, fix, docs, style, refactor, test, chore
# - scope: can be empty (eg. if the change is a global or difficult to assign to a single component)
# - subject: start with verb (such as 'change'), 50-character line
#
# body: 72-character wrapped. This should answer:
# * Why was this change necessary?
# * How does it address the problem?
# * Are there any side effects?
#
# footer: 
# - Include a link to the ticket, if any.
# - BREAKING CHANGE
#
```