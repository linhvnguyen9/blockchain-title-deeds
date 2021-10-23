package com.linh.titledeed.data.utils

fun getHttpLinkFromIpfsUri(ipfsUri: String): String {
    val cid = ipfsUri.replace("ipfs://", "")
    return "https://gateway.pinata.cloud/ipfs/$cid"
}