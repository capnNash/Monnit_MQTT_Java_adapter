#!/usr/bin/env bash

# generic docker and docker-compose
alias dc='docker-compose'
alias dce='docker-compose exec'
alias dcr='docker-compose run'
alias dcu='docker-compose up -d'
alias dcbu='docker-compose up --build -d'
alias dcb='docker-compose build'
alias dcs='docker-compose stop'
alias dcd='docker-compose down'
alias dcl='docker-compose logs -f'
alias dka='docker kill $(docker ps -q)' #kill all processes
alias drac='docker rm $(docker ps -a -q)' #remove all containers
alias drai='docker rmi $(docker images -q)' #remove all local images
alias dpdt='docker image prune --filter 'dangling=true'' #prunes all images that are not associated with a container
alias dps='docker ps'

# app specific aliases
alias cbcli="dce cb-cli /bin/bash"
alias mlog="docker-compose logs -f monnit"
alias elog="docker-compose logs -f edge"
