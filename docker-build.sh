#!/bin/sh
docker build . -t open-tracking-mn-gr
echo
echo
echo "To run the docker container execute:"
echo "    $ docker run -p 8080:8080 open-tracking-mn-gr"
