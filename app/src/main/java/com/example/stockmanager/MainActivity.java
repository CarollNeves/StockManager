package com.example.stockmanager;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout listContainer;
    private LayoutInflater inflater;
    private View cardAdd;
    private EditText addProductEdit;
    private Button btnAddProduct, btnCancel, btnAdd;

    // Lista inicial de produtos
    private String[] objetos = {"Garrafa de água", "Bolsa branca", "Fone de ouvido"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listContainer = findViewById(R.id.listContainer);
        inflater = LayoutInflater.from(this);

        // Card de adicionar produto
        cardAdd = inflater.inflate(R.layout.card_new_product, listContainer, false);
        cardAdd.setVisibility(View.GONE);
        addProductEdit = cardAdd.findViewById(R.id.editText2);
        btnCancel = cardAdd.findViewById(R.id.buttonCancel);
        btnAdd = cardAdd.findViewById(R.id.buttonAdd);

        btnAddProduct = findViewById(R.id.buttonAdicionarProduto);

        // Botão para mostrar card de adicionar
        btnAddProduct.setOnClickListener(v -> {
            if (cardAdd.getParent() == null) {
                listContainer.addView(cardAdd);
            }
            cardAdd.setVisibility(View.VISIBLE);
            addProductEdit.requestFocus();
        });

        // Botão Cancelar
        btnCancel.setOnClickListener(v -> {
            addProductEdit.setText("");
            cardAdd.setVisibility(View.GONE);
        });

        // Botão Adicionar
        btnAdd.setOnClickListener(v -> {
            String name = addProductEdit.getText().toString().trim();
            if (!name.isEmpty()) {
                addNewCard(name);
                addProductEdit.setText("");
                cardAdd.setVisibility(View.GONE);
            }
        });

        // Cria os cards iniciais
        for (String nome : objetos) {
            addNewCard(nome);
        }
    }

    // Método para adicionar um card (novo ou inicial)
    private void addNewCard(String nome) {
        View newCard = inflater.inflate(R.layout.card_objeto, listContainer, false);

        TextView tvNome = newCard.findViewById(R.id.text1);
        EditText editName = newCard.findViewById(R.id.editTextText);
        Button btnEdit = newCard.findViewById(R.id.buttonEdit);
        Button btnDelete = newCard.findViewById(R.id.buttonRemove);

        tvNome.setText(nome);

        // Botão Edit
        btnEdit.setOnClickListener(v -> {
            if (editName.getVisibility() == View.GONE) {
                editName.setVisibility(View.VISIBLE);
                editName.requestFocus();
            }
        });

        // Quando aperta Enter no campo de edição
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

        // Botão Remove
        btnDelete.setOnClickListener(v -> listContainer.removeView(newCard));

        listContainer.addView(newCard);
    }
}
