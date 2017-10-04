![Logo of the project](https://lh4.googleusercontent.com/jMMn2i2lQv9cNeEmH-BKEdI9irUGCcsSA5Fx5TZ68MdT1Z0AXkCadXunA8aYhxVLFD1P38KcobikxFA=w1504-h871)
# FinderApp

Desktop application to search log files(text files), containing specified text in file tree and network*, allows to specify files extension, read(navigation)/search text inside this files (also 1GB+).
> \* no tests for search in network
> Application uses JavaFX


## Installing / Getting started
Download gradle: https://gradle.org/install
A quick way to launch project:

```shell
git clone https://github.com/antmog/Finder.git
cd Finder/
gradle wrapper
gradlew build
gradlew run
```

Project cloned to Finder/ of current directory; run project using gradlew.

## Developing

To get project:

```shell
git clone https://github.com/antmog/Finder.git
cd Finder/
(make sure that you have gradle/gradle plugin in your IDE)
```

## Features
* User can search files, containing specified text (in main text area).
* User can specify folder (with subfolders) to search in (in main file tree).
* User can specify extension of files to search in (by default: *.log).
* User can open files found (files will be opened in right part of result block), navigation and other options are available also.

Result will be shown as file tree in bottom part of application. 

* Application allows to navigate through files w/o stopping application, also user can start new search (in main file tree).
* Application allows to work with 1GB+ files.

Search in file speed is optimized.

## Notes
* To expand file tree node - select it (select action, not click).
* To open file in result tree - select it.
* OptimizedRandomAccess file got from https://bitbucket.org/kienerj/io with several changes.
* UTF8 charset only tested.
* Not all exceptions in threads handled (also most of exceptions handled as printStackTrace()).
* No supress warnings.

## Contributing

gradle,java,JavaFX,css,fxml,png used

## About 
Application view:

![appview](https://lh4.googleusercontent.com/hZ65TkQ82vRhEVNLhYWdBGF6874J2OJeCTmd9i8NDTL2_UZ9t2WiLyW5Vnk6d_RJRwk_bv91pWnPL6w=w1187-h822-rw)
View details:

![view details](https://lh4.googleusercontent.com/8uyKMN_8ssYZ4-DIsnu31pTn76T4vfzFT6XfE0dLABakDPfH-CSKTB604klmNvLZRJTmL0rZKSzjY-c=w1824-h822-rw)
![view details](https://lh5.googleusercontent.com/HOUxy9CIhNmrUFYbsCK6VKpo1skbyIdxgQFpTgJ2MZldKaTlY4iDcwdBia2T0zBmJcg_uiLH9OFy7Go=w1824-h822)
![view details](https://lh4.googleusercontent.com/eTR-_eRE93kbHqS8-GdwQRayG7W58FSAldBher0hG9gYpVgsGttAmsQJkALkqXsdDFES4hMf5hujREM=w1824-h822-rw)
