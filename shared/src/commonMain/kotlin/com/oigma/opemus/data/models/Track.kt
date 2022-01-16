package com.oigma.opemus.data.models

import com.oigma.opemus.data.LocalFile

/**
 * Created by Harsewak Singh on 08/01/2022.
 */
data class Track(val id: String, var file: LocalFile? = null)