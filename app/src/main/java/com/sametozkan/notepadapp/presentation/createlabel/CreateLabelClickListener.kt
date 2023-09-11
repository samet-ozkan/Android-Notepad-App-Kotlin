package com.sametozkan.notepadapp.presentation.createlabel

import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity

interface CreateLabelClickListener {

    fun onCreateButtonClicked(labelEntity : LabelEntity);

}