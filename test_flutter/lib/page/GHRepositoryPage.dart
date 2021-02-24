import 'package:flutter/material.dart';
import 'package:test_flutter/model/RepoItemInfo.dart';
import 'package:test_flutter/network/GitHubRepository.dart';

class GHRepositoryPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return GHRepositoryState();
  }
}

class GHRepositoryState extends State<GHRepositoryPage>
    with AutomaticKeepAliveClientMixin {
  List<RepoItemInfo> repoList;
  var len = 0;

  @override
  bool get wantKeepAlive => true;

  @override
  void initState() {
    super.initState();
    _fetchData();
  }

  _fetchData() async{
    var repositories = await GitHubRepository.getInstance().getRepositories();
    setState(() {
      repoList = repositories;
      len = repoList.length;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("GitHub Repository")),
      body:ListView.builder(
          itemBuilder: (context,index){
            var itemInfo = repoList[index];
            return Container(
              child: Container(
                child: Row(
                  children: <Widget>[
                    Image.network(itemInfo.owner.avatarUrl),
                    Text(itemInfo.title)
                  ],
                ),
              ),
            );
          },
        itemCount: len,
      )
    );
  }
}
