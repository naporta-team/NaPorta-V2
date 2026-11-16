package com.example.naporta

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.Locale

class DishDetailsActivity : BaseActivity() {
    private var quantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_details)

        val itemId = intent.getStringExtra("item_id") ?: ""
        val itemName = intent.getStringExtra("item_name") ?: ""
        val itemPrice = intent.getStringExtra("item_price") ?: "0"
        val itemDescription = intent.getStringExtra("item_description") ?: ""
        val itemImageUrl = intent.getStringExtra("item_image_url") ?: ""
        val itemCategory = intent.getStringExtra("item_category") ?: ""

        val txtNome = findViewById<TextView>(R.id.txtNomePrato)
        val txtPreco = findViewById<TextView>(R.id.txtPreco)
        val txtDescricao = findViewById<TextView>(R.id.txtDescricao)
        val txtQuantidade = findViewById<TextView>(R.id.txtQuantidade)
        val imgPrato = findViewById<ImageView>(R.id.imgPrato)

        txtNome.text = itemName
        txtPreco.text = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
            .format(itemPrice.toDoubleOrNull() ?: 0.0)
        txtDescricao.text = itemDescription

        if (itemImageUrl.isNotBlank()) {
            Glide.with(this)
                .load(itemImageUrl)
                .placeholder(R.drawable.img_hamburguer)
                .error(R.drawable.img_hamburguer)
                .centerCrop()
                .into(imgPrato)
        }

        findViewById<ImageView>(R.id.btnVoltar).setOnClickListener { finish() }

        findViewById<ImageView>(R.id.btnAumentar).setOnClickListener {
            quantity++
            txtQuantidade.text = quantity.toString()
        }

        findViewById<ImageView>(R.id.btnDiminuir).setOnClickListener {
            if (quantity > 1) {
                quantity--
                txtQuantidade.text = quantity.toString()
            }
        }

        findViewById<android.view.View>(R.id.btnAdicionarCarrinho).setOnClickListener {
            val price = itemPrice.toDoubleOrNull() ?: 0.0
            repeat(quantity) {
                CartStore.add(CartItem(
                    productId = itemId.hashCode().toLong(),
                    menuItemId = itemId,
                    name = itemName,
                    price = price,
                    imageUrl = itemImageUrl
                ))
            }
            Toast.makeText(this, "$itemName adicionado ao carrinho!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
