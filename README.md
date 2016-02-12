# chisel3-skeleton

Both Chisel3 and FIRRTL are in **alpha** so there is no guarantee that this repo works, like, ever.

### Setup Instructions

Please let me know if you notice any issues or errors with these instructions.

#### Prerequisites
This toolchain requires JDK7 (it does NOT work with JDK8), sbt, and Verilator (3.880 or newer).    

##### Linux Setup
1. Install JDK7    
`sudo apt-get install openjdk-7-jdk`    

1. [Install sbt](http://www.scala-sbt.org/0.13/docs/Installing-sbt-on-Linux.html "Installing sbt on Linux")    
`echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list`    
`sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 642AC823`    
`sudo apt-get update`    
`sudo apt-get install sbt`    

1. [Install Verilator](http://www.veripool.org/projects/verilator/wiki/Installing "Installing Verilator")    
apt-get installs an old version that will not work, so we build from source:    
`sudo apt-get install git make autoconf g++ flex bison   # Verilator prerequisites`    
`git clone http://git.veripool.org/git/verilator`    
`unset VERILATOR_ROOT          # For bash, unsetenv for csh`    
`cd verilator`    
`git pull                      # Make sure we're up-to-date`    
`git checkout verilator_3_880  # Switch to version 3.880`    
`autoconf                      # Create ./configure script`    
`./configure`    
`make`    
`sudo make install`    

##### OSX Setup
We recommend using homebrew as it supports updated versions of the prerequisites (including Verilator 3.880):

1. Install JDK7 (if you don't already have it)    
`$ brew cask install caskroom/versions/java7`    
1. Install sbt    
`$ brew install sbt`    
1. Install Verilator    
`$ brew install verilator`

#### Project Setup
 1. Clone the repository  
 `git clone git@github.com:jackkoenig/chisel3-skeleton.git`  
 1. Update submodules    
 `cd chisel3-skeleton`    
 `git submodule update --init`  
 1. Run sbt the first time to download dependencies  
 `sbt`
 1. Everything should be set up!  
 `> run-main test.Driver`
