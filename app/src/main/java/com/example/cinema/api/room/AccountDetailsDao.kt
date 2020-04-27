package com.example.cinema.api.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinema.api.model.AccountDetails

@Dao
interface AccountDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccountDetails(accountDetails: AccountDetails)

    @Query("SELECT * FROM account_details_table ORDER BY id DESC LIMIT 1")
    fun getAccountsDetails(): AccountDetails
}