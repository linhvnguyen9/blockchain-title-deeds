#!/bin/sh
solc VTitleDeeds.sol @openzeppelin=/home/linh/node_modules/@openzeppelin --allow-paths /home/linh/node_modules/@openzeppelin/ --bin --abi --optimize --overwrite  -o ./
web3j generate solidity -a VTitleDeeds.abi -b VTitleDeeds.bin -o ../java --package=com.linh.titledeed.data.contract