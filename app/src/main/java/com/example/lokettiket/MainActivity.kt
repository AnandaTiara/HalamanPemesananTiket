package com.example.lokettiket

import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val hargaPerTiket = 25_000
    private val jumlahMinimal = 1
    private val jumlahMaksimal = 10

    private var jumlahTiket = jumlahMinimal

    private lateinit var tvHarga: TextView
    private lateinit var tvJumlah: TextView
    private lateinit var tvTotal: TextView
    private lateinit var btnKurang: ImageButton
    private lateinit var btnTambah: ImageButton
    private lateinit var btnReset: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvHarga = findViewById(R.id.tv_harga)
        tvJumlah = findViewById(R.id.tv_jumlah)
        tvTotal = findViewById(R.id.tv_total)
        btnKurang = findViewById(R.id.btn_kurang)
        btnTambah = findViewById(R.id.btn_tambah)
        btnReset = findViewById(R.id.btn_reset)

        tvHarga.text = rupiah(hargaPerTiket)

        btnTambah.setOnClickListener {
            if (jumlahTiket < jumlahMaksimal) {
                jumlahTiket++
                perbaruiTampilan()
            }
        }

        btnKurang.setOnClickListener {
            if (jumlahTiket > jumlahMinimal) {
                jumlahTiket--
                perbaruiTampilan()
            }
        }

        btnReset.setOnClickListener {
            jumlahTiket = jumlahMinimal
            perbaruiTampilan()
        }

        savedInstanceState?.let { jumlahTiket = it.getInt(KEY_JUMLAH, jumlahMinimal) }
        perbaruiTampilan()
    }

    private fun perbaruiTampilan() {
        tvJumlah.text = jumlahTiket.toString()
        tvTotal.text = rupiah(jumlahTiket * hargaPerTiket)

        btnKurang.isEnabled = jumlahTiket > jumlahMinimal
        btnTambah.isEnabled = jumlahTiket < jumlahMaksimal
        btnKurang.alpha = if (btnKurang.isEnabled) 1f else 0.4f
        btnTambah.alpha = if (btnTambah.isEnabled) 1f else 0.4f
    }

    private fun rupiah(nilai: Int): String {
        val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        format.maximumFractionDigits = 0
        return format.format(nilai)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_JUMLAH, jumlahTiket)
    }

    companion object {
        private const val KEY_JUMLAH = "jumlah_tiket"
    }
}