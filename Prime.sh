#!/bin/bash

echo -n "Enter a number: "
read num

# Handle special cases
if [ $num -le 1 ]; then
    echo "$num is NOT a prime number."
    exit
fi

# Assume number is prime
isPrime=1

for (( i=2; i*i<=num; i++ ))
do
    if (( num % i == 0 )); then
        isPrime=0
        break
    fi
done

if [ $isPrime -eq 1 ]; then
    echo "$num is a PRIME number."
else
    echo "$num is NOT a prime number."
fi
