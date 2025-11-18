package com.example.stockmanager;



import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    private LinearLayout listContainer;
    private String[] objetos = {
            "Garrafa de água", "Bolsa branca", "Fone de ouvido", "óculos de sol"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listContainer = findViewById(R.id.listContainer);

        LayoutInflater inflater = LayoutInflater.from(this);

        View cardAdd = inflater.inflate(R.layout.card_new_product, listContainer, false);
        cardAdd.setVisibility(View.GONE);

        Button btnAddProduct = findViewById(R.id.buttonAdicionarProduto);
        EditText addProduct = cardAdd.findViewById(R.id.editText2);
        Button btnCancel = cardAdd.findViewById(R.id.buttonCancel);
        Button btnAdd = cardAdd.findViewById(R.id.buttonAdd);


        //Quando o batão add product é clicado
        btnAddProduct.setOnClickListener( v -> {
            cardAdd.setVisibility(View.VISIBLE);
            addProduct.requestFocus();   // coloca o cursor dentro do campo
        });

        //quando o usuario aperta btnAdd
        btnAdd.setOnClickListener(v -> {

            String name = addProduct.getText().toString().trim();

            if (!name.isEmpty()) {

                View newCard = inflater.inflate(R.layout.card_objeto, listContainer, false);

                TextView tv = newCard.findViewById(R.id.text1);
                tv.setText(name);

                listContainer.addView(newCard);

                addProduct.setText("");
                cardAdd.setVisibility(View.GONE);
            }
        });

        // quando o usuario aperta btnCancel
        btnCancel.setOnClickListener(view -> {
            cardAdd.setVisibility(View.GONE);
            addProduct.setText("");
        });

        btnAddProduct.setOnClickListener(v -> {
            if (cardAdd.getParent() == null) {
                listContainer.addView(cardAdd);
            }

            cardAdd.setVisibility(View.VISIBLE);
            addProduct.requestFocus();
        });

        // Cria os cards dinamicamente
        for (String nome : objetos) {
            View card = inflater.inflate(R.layout.card_objeto, listContainer, false);


            TextView tvNome = card.findViewById(R.id.text1);
            EditText editName = card.findViewById(R.id.editTextText);
            Button btnEdit = card.findViewById(R.id.buttonEdit);
            Button btnDelete = card.findViewById(R.id.buttonRemove);

            tvNome.setText(nome);

            // Quando o botão edit é clicado
            btnEdit.setOnClickListener(v -> {
                if (editName.getVisibility() == View.GONE) {
                    editName.setVisibility(View.VISIBLE);
                    editName.requestFocus(); // coloca o cursor dentro do campo
                }
            });

            // Quando o usuário aperta "Enter"
            editName.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                    String novoNome = editName.getText().toString().trim();

                    if (!novoNome.isEmpty()) {
                        tvNome.setText(novoNome);
                    }

                    editName.setVisibility(View.GONE);
                    return true;
                }
                return false;
            });

            //Quando o botão remove é clicado
            btnDelete.setOnClickListener(view -> {

                listContainer.removeView(card);
            });

            listContainer.addView(card);

        }
    }
}
