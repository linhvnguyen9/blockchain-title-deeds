package com.linh.titledeed.data.utils

fun getHttpLinkFromIpfsUri(ipfsUri: String): String {
    val cid = ipfsUri.replace("ipfs://", "")
    return "http://192.168.1.112:8080/ipfs/$cid"
}