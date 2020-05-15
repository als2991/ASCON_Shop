package com.suvorov.ascon_shop.ui

import android.annotation.SuppressLint
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


class CreateOrderActivity: MvpAppCompatActivity(),
    CreateOrderView {

    private var totalPrice = ""
    private var editList = listOf<EditText>()

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


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_order)

        totalPrice = intent.getStringExtra(TOTALPRICE_TAG)

         editList = listOf<EditText>(
            createOrderOrganization,
            createOrderFio,
            createOrderPosition,
            createOrderPhone,
            createOrderEmail
        )

        presenter.getInfoWithActivity(totalPrice, editList)

        setListeners(editList)

        //Total price
        createOrderTotalPriceWithSale.text = "${totalPrice} р"

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
                    presenter.getInfoWithActivity(totalPrice, editList)
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
            else -> Unit
        }
    }

    override fun moveMainActivity() {
        startActivity(Intent(this,CategoryActivity::class.java)).apply {
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
    }

    override fun showMessage(text: String?) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TOTALPRICE_TAG = "TOTALPRICE_TAG"
    }
}