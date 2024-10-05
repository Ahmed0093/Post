package com.example.post.data

import com.example.post.data.datasource.local.Post
import com.example.post.data.model.PostApiResponse
import com.example.post.data.model.PostDataModel
import com.example.post.domain.convertDateToMillis
import com.example.post.domain.model.PostDomainModel


fun PostDataModel.toPostModel(number: Int): Post {
    return Post(
        title = this.title,
        imgUrl = this.thumbnail,
        pageNumber = number.toString()
    )
}

fun Post.toPostDomainModel(): PostDomainModel {
    return PostDomainModel(id = this.id, title = this.title, imageUrl = this.imgUrl)
}


fun mapDataModelToDomainModel(postDataModel: PostDataModel): PostDomainModel {
    return PostDomainModel(
        title = postDataModel.title,
        imageUrl = postDataModel.thumbnail,
        id = convertDateToMillis(postDataModel.date)
    )
}

fun mapPostResponseModelToDomain(
    result: PostApiResponse
): ArrayList<PostDomainModel> {
    val postDomainModelArrayLists: ArrayList<PostDomainModel> = arrayListOf<PostDomainModel>()
    result.data.forEach {
        postDomainModelArrayLists.add(mapDataModelToDomainModel(it))
    }
    return postDomainModelArrayLists
}