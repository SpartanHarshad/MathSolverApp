package com.harshad.mathsolver.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RESULT_TABLE")
data class ResultEntity(
    @ColumnInfo("resultStr") var resultStr: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rId")
    var rId: Int = 0
}
