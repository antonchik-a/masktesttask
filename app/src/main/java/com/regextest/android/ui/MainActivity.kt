package com.regextest.android.ui

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.regextest.android.R
import com.regextest.android.entity.ParseResult
import com.regextest.android.presentation.MainPresenter
import com.regextest.android.presentation.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView{


    lateinit var adapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MainPresenter.view = this

        adapter = ResultAdapter(this, object: ResultAdapter.ResultClick{
            override fun checkedResult(index: Int, isChecked: Boolean) {
                MainPresenter.checkedResult(index, isChecked)
            }

            override fun loadMore(positon: Int) {
                MainPresenter.loadMore(positon)
            }
        })
        listView?.adapter = adapter

        url?.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                MainPresenter.onUrlInput(s.toString())
            }
        })

        regex?.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                MainPresenter.onMaskInput(s.toString())
            }
        })

        appBarRightIcon?.setOnClickListener {
            MainPresenter.onCopyClick()
        }

        button?.setOnClickListener {
            MainPresenter.onParseClick()
        }
    }

    override fun showProgress(b: Boolean) {
        if(b) {
            progressBar?.visibility = VISIBLE
        }else{
            progressBar?.visibility = GONE
        }
    }

    override fun setButtonEnabled(b: Boolean) {
        button?.isEnabled = b
    }


    override fun onDestroy() {
        super.onDestroy()
        MainPresenter.view = null
        if(isFinishing){
            MainPresenter.finishParser()
        }
    }

    override fun showError(error: Int) {
        Toast.makeText(this, resources.getString(error),Toast.LENGTH_LONG).show()
    }

    override fun showData(resultsPage: MutableList<ParseResult>) {
        adapter.showData(resultsPage)
    }

    override fun appendData(results: MutableList<ParseResult>) {
        adapter.appendData(results)
    }

    override fun appendValue(results: ParseResult) {
        adapter.appendValue(results)
    }

    override fun clearAdapter() {
        adapter.clear()
    }

    override fun showOutputRegex(parseMask: String?) {
        parsedRegex?.setText( "Regex for mask: $parseMask")
    }

    override fun setCopyButtonVisible(b: Boolean) {
        if(b){
            appBarRightIcon?.visibility = VISIBLE
        }else{
            appBarRightIcon?.visibility = GONE
        }
    }

    override fun showMessage(message: Int) {
        Toast.makeText(this, resources.getString(message),Toast.LENGTH_LONG).show()
    }


    override fun setClipboard( text: String) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as android.text.ClipboardManager
            clipboard.text = text
        } else {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = android.content.ClipData.newPlainText("Copied Text", text)
            clipboard.primaryClip = clip
        }
        Toast.makeText(this, "Text copied",Toast.LENGTH_LONG).show()
    }


}
