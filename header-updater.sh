#!/bin/bash
# Updates the third line in all java files.

for file in $(find . -name "*.java"); do
	sed -i -e '3 s/*   Copyright 2009-2010 by Christian Ihle                                 */*   Copyright 2009-2011 by Christian Ihle                                 /' $file
done
