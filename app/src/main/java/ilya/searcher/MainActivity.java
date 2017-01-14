package ilya.searcher;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    DatabaseHelper myDb;
    EditText editName, editTelephone;
    Button buttonAdd, buttonSearch, buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = (EditText)findViewById(R.id.editText_name);
        editTelephone = (EditText)findViewById(R.id.editText_telephone);

        buttonAdd = (Button)findViewById(R.id.button_add);
        buttonSearch = (Button)findViewById(R.id.button_search);
        buttonDelete = (Button)findViewById(R.id.button_delete);

        setAddListener();
        setSearchListener();
        setDeleteListener();
    }

    public void setAddListener()
    {
        buttonAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                boolean isInserted = myDb.insertData(editName.getText().toString(),
                        editTelephone.getText().toString());
                if(isInserted)
                    Toast.makeText(MainActivity.this, "Добавлено", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Не добавлено", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setSearchListener()
    {
        buttonSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Cursor result = myDb.search(editName.getText().toString());
                if(result.getCount() == 0)
                {
                    showMessage("Ошибка", "Ничего не найдено");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while(result.moveToNext())
                {
                    buffer.append("Имя: " + result.getString(1) + "\n");
                    buffer.append("Телефон: " + result.getString(2) + "\n");
                }
                showMessage("Результаты", buffer.toString());
            }
        });
    }


    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void setDeleteListener()
    {
        buttonDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Integer deleteRows = myDb.deleteData(editName.getText().toString());
                if(deleteRows > 0)
                    Toast.makeText(MainActivity.this, "Удалено", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Не удалено", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

