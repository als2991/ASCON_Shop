package com.suvorov.ascon_shop.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.Toast
import com.suvorov.ascon_shop.R
import com.suvorov.ascon_shop.data.ViewedProductDaoIml
import com.suvorov.ascon_shop.domain.MainApi
import com.suvorov.ascon_shop.presenter.CreateOrderPresenter
import com.suvorov.ascon_shop.presenter.FieldType
import kotlinx.android.synthetic.main.activity_create_order.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CreateOrderActivity: MvpAppCompatActivity(), CreateOrderView {

    private val presenter by moxyPresenter {
        val retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.url_server))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MainApi::class.java)
        CreateOrderPresenter(
            viewedProductDao = ViewedProductDaoIml(sharedPreferences),
            mainApi = service
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_order)

        val totalPrice = intent.getStringExtra(TOTALPRICE_TAG)

        val editList = listOf<EditText>(
            createOrderOrganization,
            createOrderFio,
            createOrderPosition,
            createOrderPhone,
            createOrderEmail
        )

        setListeners(editList)

        //Total price
        createOrderTotalPriceWithSale.text = totalPrice

        back.setOnClickListener { finish() }

        createOrderPlace.setOnClickListener { presenter.onClickOrderPlace() }

    }

    private fun setListeners(editors: List<EditText>) {
        editors.forEach {
            val editType: FieldType = when(it.id){
                R.id.createOrderOrganization -> FieldType.ORGANIZATION
                R.id.createOrderFio -> FieldType.FIO
                R.id.createOrderPosition -> FieldType.POSITION
                R.id.createOrderPhone -> FieldType.PHONE
                R.id.createOrderEmail -> FieldType.EMAIL
                else -> FieldType.NONE
            }
            it.addTextChangedListener(object : SimpleTextWatcher() {
                override fun afterTextChanged(s: Editable?) {
                    super.afterTextChanged(s)
                    presenter.checkEditText(it.text.toString(),editType)
                }
            })
        }
    }

    private fun EditText.showError(visible: Boolean){
        val drawable = if(visible) R.drawable.ic_error
        else 0

        this.setCompoundDrawablesWithIntrinsicBounds(0,0,drawable,0)
    }

    override fun showErrorForEditText(visible: Boolean, fieldType: FieldType) {
        when (fieldType){
            FieldType.ORGANIZATION -> createOrderOrganization.showError(visible)
            FieldType.FIO -> createOrderFio.showError(visible)
            FieldType.POSITION -> createOrderPosition.showError(visible)
            FieldType.PHONE -> createOrderPhone.showError(visible)
            FieldType.EMAIL -> createOrderEmail.showError(visible)
            else -> Toast.makeText(this,"Проверьте заполнение всех полей", Toast.LENGTH_SHORT).show()
        }
    }

    override fun moveMainActivity() {
        val intent = Intent(
            this,
            CategoryActivity::class.java
        )
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent);
    }

    companion object {
        const val TOTALPRICE_TAG = "TOTALPRICE_TAG"
    }
}