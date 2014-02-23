voctec
======
## Setup

### Set up for collaboration

If you are contributing to this project, and are fairly new to how this whole contributing thing works, then please follow the outlined instructions. Here is how to do a simple fork and pull.

1. Fork this repository (Fork button on the top right)
2. On your computer, navigate to a directory where you want to work on the project.
3. run `git init` followed by `git remote add origin git@github.com:YOURUSERNAME/voctec_sizing.git` (replace YOURUSERNAME with your username).
4. Now pull everything with `git pull`

Every change you make from then on should be pushed to your fork of the repository. To merge your code into the master, simply submit a pull request. More information is available on the [Github Support page](https://help.github.com/articles/using-pull-requests).

### Git (Optional)

Git is optional, but for completeness it is included. First of all, refer to [this github article](https://help.github.com/articles/set-up-git) on how to set up git on your computer. If you're on windows, you may additionally want to install [Github for Windows](http://windows.github.com/).

As for Eclipse, you are also going to need to install the proper git plugins. To do so, follow the steps outlined below.

1. Open Eclipse
2. Go to `Help` > `Install New Software`
3. Under Work with, select your Eclipse version site ( something like `Kepler - http://download.eclipse.org/releases/kepler`)
3. type `git` in the search box
4. Install everything that doesn't end in ` - Source Code`
5. Accept licenses and install.

### Maven

Install dependencies:

This project uses Slick2D from the [Slick 2D Maven](https://github.com/nguillaumin/slick2d-maven) repository. Below is a quick excerpt on getting it installed.

```
git clone git://github.com/nguillaumin/slick2d-maven.git
cd slick2d-maven
mvn clean install
```

### Eclipse

Use [Maven Natives](https://code.google.com/p/mavennatives/#Eclipse_Plugin) to automatically build the project for your platform. To install Maven Natives, just follow the simple steps below.

1. Open Eclipse
2. Go to `Help` > `Install New Software`
3. Paste the following URL: `http://mavennatives.googlecode.com/svn/eclipse-update/`
4. Install Maven Natives

