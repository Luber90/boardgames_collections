package com.example.bgc

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class AddActivity: AppCompatActivity() {
    private var name: String = "kawerna"
    private var listString: MutableList<String> = ArrayList()
    var adapter: ArrayAdapter<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_add)
        super.onCreate(savedInstanceState)
        loadData()
        showData()
        downloadData()

    }

    fun downloadData(){
        val cd=APIHelper()
        cd.execute()
    }

    fun refresh(v: View){
        downloadData()
    }

    fun loadData(){

        val filename = "gry.xml"
        val path = filesDir
        val inDir = File(path, "XML")
        var gmname = ""
        listString.clear()
        if(inDir.exists()){
            val file = File(inDir, filename)
            if(file.exists()){
                val xmlDoc: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)

                xmlDoc.documentElement.normalize()

                val items: NodeList = xmlDoc.getElementsByTagName("item")

                for(i in 0..items.length - 1){
                    val itemNode: Node = items.item(i)

                    if(itemNode.getNodeType() == Node.ELEMENT_NODE){

                        val elem = itemNode as Element
                        val children=elem.childNodes

                        for(j in 0..children.length-1){
                            val node = children.item(j)
                            if(node is Element){
                                when(node.nodeName){
                                    "name" -> {
                                        gmname = node.getAttribute("value")
                                        listString.add(gmname)
                                    }
                                }
                            }
                        }
                    }
                }

                /*if(name!=""){
                    listString.add(gmname)
                }*/
            }
        }

    }

    fun showData(){
        val list: ListView = findViewById(R.id.games)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listString)
        list.adapter = adapter
    }


    private inner class APIHelper:AsyncTask<String, Int, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            loadData()
            showData()
        }

        override fun doInBackground(vararg params: String?): String {
            val ans: ArrayList<String> = ArrayList()
            try {
                val user = ArrayList<String>()
                val url = URL("https://www.boardgamegeek.com/xmlapi2/search?query=$name&type=boardgame")
                val connection = url.openConnection()
                connection.connect()
                val lengthOfFile = connection.contentLength
                val isStream = url.openStream()
                val testDirectory = File("$filesDir/XML")
                if (!testDirectory.exists()) testDirectory.mkdir()
                val fos = FileOutputStream("$testDirectory/gry.xml")
                val data = ByteArray(1024)
                var count = 0
                var total: Long = 0
                var progress = 0
                count = isStream.read(data)
                while (count != -1) {
                    total += count.toLong()
                    val progress_temp = total.toInt() * 100 / lengthOfFile
                    if (progress_temp % 10 == 0 && progress != progress_temp) {
                        progress = progress_temp
                    }
                    fos.write(data, 0, count)
                    count = isStream.read(data)
                }
                isStream.close()
                fos.close()
            }
            catch(e: MalformedURLException){
                return "Malformed URL"
            }
            catch(e: FileNotFoundException){
                return "File not found"
            }
            catch(e: IOException){
                return "IO Exception"
            }
            return "success"
        }
    }

}