# Changelog
This changelog contains a loose collection of changes in every release. I will also try and document all breaking changes to the API.

The format is based on [Keep a Changelog](http://keepachangelog.com/) and this project adheres to a "shifted" version of semantic versioning while the major version remains at 0: Minor version changes indicate breaking changes, patch version changes should not contain breaking changes.


## 0.2.1

### Added

* Support authentication with HTTPS using JGits [`CredentialsProvider`](http://download.eclipse.org/jgit/site/4.1.1.201511131810-r/apidocs/org/eclipse/jgit/transport/CredentialsProvider.html). See [README](./README.md) for details

### Fixed

* Fixed NullPointerException in case no `known_hosts` file exists (#21)

## 0.2.0

### Added

* Adds compatibility with LambdaCD versions 0.12.0 and greater. Incompatible with versions earlier than 0.9.1. (#19) 

## 0.1.6

### Added
* Add support for git tag and push.
    Use case is to tag a deployed version to a commit.
    Push pushes all new tags and commits to a given repository.
    (Thanks to @rohte)

## 0.1.5

### Added
* Allow setting of a specific identity file (#12).
    Use case is to select the desired account from multiple GitHub accounts that have differing private repo membership.
    (Thanks to @markdingram)

## 0.1.4

### Fixed
* Fixed bug in error handling in `wait-for-git` (#15)
    (Thanks to @ImmoStanke)

## 0.1.3

### Added
* Allow specifying a timeout when cloning a repository

## 0.1.2

### Added

* Made step-killing behavior of `wait-for-git` independent of polling frequency (#10)
* Supporting known hosts files other than `~/.ssh/known_hosts` (e.g. `/etc/ssh/ssh_known_hosts`). (#9)
  
### Deprecated

* `lambdcd-git/ssh-agent-support/initialize-ssh-agent-support!` is deprecated and will be removed in subsequent releases.
    It is being replaced with `lambdacd-git.core/init-ssh!`

## 0.1.1

### Added

* Added a way to notify `wait-for-git` through HTTP POST requests (#6)

## 0.1.0

* Initial Release 
