#!/bin/bash

echo -n "Enter a string: "
read str

revstr=$(echo "$str" | rev)

if [ "$str" = "$revstr" ]; then
    echo "\"$str\" is a PALINDROME."
else
    echo "\"$str\" is NOT a palindrome."
fi
