package com.univ.shoes.Orders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class OrdersViewModel(application: Application)
    : AndroidViewModel(application) {

    private val repository =
        OrderRepository(application)

}

