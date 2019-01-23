package com.example.recipeappv3;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import static com.example.recipeappv3.MyContentProvider.CONTENT_URL;

/*
Recipe lists is responsible for displaying all recipe titles in listview this is done by using contentresolver to pass variables from the content provider to other apps/classes
The geList method retrieves the inserted data from using the content resolver and is read by a cursor which is adapted to be able to be inserted into a listview.
The delete method gets the texts from the deleteEditText (*ID NUMBER*) and matches it with the database ID to delete that recipe
clicking on recipes results in a new activity starting called Recipe.
*/

public class RecipeList extends Activity {

    TextView recipesTextView = null;
    EditText DeleteText, SearchText;
    ContentResolver resolver;
    private ListView listView ;

    CursorAdapter cursoradapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipesTextView = (TextView) findViewById(R.id.recipesTextView);
        DeleteText = (EditText) findViewById(R.id.DeleteText);
        SearchText = (EditText) findViewById(R.id.SearchText);
        listView = (ListView) findViewById( R.id.listView );

        Uri uri = CONTENT_URL;// explanation in getList method

        String[] projection = {MyContentProvider.id, MyContentProvider.name, MyContentProvider.ingredient}; // take the items that were stored in database and put in a string array
        String[] From = {MyContentProvider.id, MyContentProvider.name};
        int[] to = {R.id.listid, R.id.listName};

        Cursor customCursor = getContentResolver().query(uri,projection, null, null, null);
        cursoradapter = new SimpleCursorAdapter(this, R.layout.title_list, customCursor, From, to, 0);

        listView.setAdapter(cursoradapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) { // when an item of the list is clicked on open the recipe activity to see the ingredients and title.

                Cursor cursorList = cursoradapter.getCursor();
                cursorList.moveToPosition(position);

                String title = cursorList.getString(cursorList.getColumnIndex("" + MyContentProvider.name));
                String ingerdient = cursorList.getString(cursorList.getColumnIndex("" + MyContentProvider.ingredient));

                Intent intent = new Intent(RecipeList.this, Recipe.class);
                intent.putExtra("Name", title);
                intent.putExtra("Ingredient",ingerdient);
                startActivity(intent);
            }
        });


        resolver = getContentResolver();
    }

    public void deleteRecipe(View view)// Retrieves the desired id for deletion and checks if the text box for deletion is empty, the matching id of the
    // database with the textbox will be deleted and then a new list is shown in the listview
    {
        String DeleteId = DeleteText.getText().toString();

        if(!TextUtils.isEmpty(DeleteId))
        {
            long idDelete = resolver.delete(CONTENT_URL,"_id = ?",new String[]{DeleteId});
            getList();
        }
        else
        {
            Toast.makeText(getBaseContext(), "Enter an ID to delete...", Toast.LENGTH_LONG ).show();
        }

    }



    public void getList() // Retrieves the inserted data from using the content resolver and is read by a cursor which is adapted to be able to be inserted into a listview
    {
        Uri uri = CONTENT_URL;

        String[] projection = {MyContentProvider.id, MyContentProvider.name, MyContentProvider.ingredient};
        String[] From = {MyContentProvider.id, MyContentProvider.name};
        int[] to = {R.id.listid, R.id.listName};

        Cursor customCursor = getContentResolver().query(uri,projection, null, null, null);
        cursoradapter = new SimpleCursorAdapter(this, R.layout.title_list, customCursor, From, to, 0);
        listView.setAdapter(cursoradapter);
    }
}