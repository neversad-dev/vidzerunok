package com.neversad.vidzerunok.feature.gallery.data

interface ImageDataSource {

    suspend fun getImages(): List<String>

    suspend fun copyImageToGallery(imagePath: String, fileName: String)

    suspend fun deleteImage(imagePath: String)
}