package com.example.catfacts.extensions

import com.example.catfacts.data.api.catfactsapi.model.FactResponse
import com.example.catfacts.data.domain.CatFact

fun CatFact.Companion.fromRemoteValue(remoteValue: FactResponse): CatFact {
    return CatFact(
        text = remoteValue.text
    )
}
