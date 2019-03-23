package android.justo.es.anymore

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    private var resultado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        if (intent.extras != null)
            resultado = intent.getIntExtra("result", 0)

        if (resultado == 4)
            resultTextView.text = "Creemos que estás sufriendo violencia de género, deberías contactar con el número de teléfono 016, o hablar con un asistente social."
        else if (resultado == 3)
            resultTextView.text = "Con los resultados obtenidos no podemos darte una confirmación. Es posible que estés sufriendo un caso de violencia de género, te aconsejamos contactar con un asistente social. También puedes llamar al número de teléfono 016 para pedir información."
        else if (resultado == 2 || resultado == 1)
            resultTextView.text = "Creemos que no estás sufriendo violencia de género. "
    }
}
