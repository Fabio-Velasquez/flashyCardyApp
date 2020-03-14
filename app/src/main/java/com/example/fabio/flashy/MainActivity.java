package com.example.fabio.flashy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);

            }

        });
        findViewById(R.id.flashcard_answer).setBackgroundColor(getResources().getColor(R.color.my_red_color,null));

        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
            }

        });



        findViewById(R.id.nextAct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent,100);

            }


        });

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());

        allFlashcards = flashcardDatabase.getAllCards();
        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            int currentCardDisplayedIndex = 0;
            @Override
            public void onClick(View v) {

                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                if (findViewById(R.id.flashcard_question).getVisibility() == View.INVISIBLE) {
                    findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                    findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                }
                // set the question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
            }
        });




        findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_question)).getText().toString());
            }
        });



    }

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 100){
            if (requestCode == 100 && resultCode==RESULT_OK) {
                String string1 = data.getExtras().getString("string1");
                String string2 = data.getExtras().getString("string2");
                ((TextView) findViewById(R.id.flashcard_question)).setText(string1);
                ((TextView) findViewById(R.id.flashcard_answer)).setText(string2);


                flashcardDatabase.insertCard(new Flashcard(string1, string2));


                allFlashcards = flashcardDatabase.getAllCards();



                if (allFlashcards != null && allFlashcards.size() > 0) {
                    ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
                    ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
                }




            }
        }
    }

}
