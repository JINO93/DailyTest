import 'package:english_words/english_words.dart';
import 'package:flutter/material.dart';

class RandomWords extends StatefulWidget {

  @override
  State<StatefulWidget> createState() {
    return RandomWordsState();
  }
}

class RandomWordsState extends State<RandomWords> with AutomaticKeepAliveClientMixin{
  var _suggestions = <WordPair>[];
  final _lovedWords = new Set<WordPair>();
  final _biggerFont = const TextStyle(fontSize: 18.0);

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text('My first flutter app'),
        actions: <Widget>[
          new IconButton(icon: new Icon(Icons.list), onPressed: _onMenuPress)
        ],
      ),
      body: new Center(
//          child: new Text(wordPair.asCamelCase),
        child: _buildSuggestions(),
      ),
    );
  }

  Widget _buildSuggestions() {
    return new ListView.builder(
        padding: const EdgeInsets.all(16),
        itemBuilder: (context, i) {
          if (i.isOdd) {
            return new Divider();
          }
          var index = i ~/ 2;
          if (index >= _suggestions.length) {
            // ...接着再生成10个单词对，然后添加到建议列表
            _suggestions.addAll(generateWordPairs().take(10));
          }
          return _buildRow(_suggestions[index]);
        });
  }

  Widget _buildRow(WordPair pair) {
    var liked = _lovedWords.contains(pair);
    return new ListTile(
      title: new Text(
        pair.asPascalCase,
        style: _biggerFont,
      ),
      trailing: new Icon(
        liked ? Icons.favorite : Icons.favorite_border,
        color: liked ? Colors.red : null,
      ),
      onTap: () {
        setState(() {
          if (liked) {
            _lovedWords.remove(pair);
          } else {
            _lovedWords.add(pair);
          }
        });
      },
    );
  }

  void _onMenuPress() {
    Navigator.of(context).push(
      new MaterialPageRoute(
        builder: (context) {
          var likedList = _lovedWords.map(
                (pair) {
              return new ListTile(
                title: new Text(
                  pair.asCamelCase,
                  style: _biggerFont,
                ),
              );
            },
          );

          var dividers =
          ListTile.divideTiles(tiles: likedList, context: context).toList();

          return new Scaffold(
            appBar: new AppBar(
              title: new Text("liked list"),
            ),
            body: new ListView(
              children: dividers,
            ),
          );
        },
      ),
    );
  }

  @override
  bool get wantKeepAlive => true;
}