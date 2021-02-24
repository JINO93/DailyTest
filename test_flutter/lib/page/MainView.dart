import 'package:flutter/material.dart';
import 'package:test_flutter/page/GHRepositoryPage.dart';
import 'package:test_flutter/page/HomePage.dart';
import 'package:test_flutter/page/RandomWordPage.dart';

class MainView extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return MainViewState();
  }
}

class MainViewState extends State<MainView> {
  var currentIndex = 0;

  final PageController pageController = PageController();

  // 禁止 PageView 滑动
  final ScrollPhysics neverScroll = const NeverScrollableScrollPhysics();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: PageView(
        physics: neverScroll,
        controller: pageController,
        children: <Widget>[
          MyHomePage(title: "JINO"),
          RandomWords(),
          GHRepositoryPage(),
        ],
      ),
      bottomNavigationBar: BottomNavigationBar(
          type: BottomNavigationBarType.shifting,
          currentIndex: currentIndex,
          onTap: (int index) {
            pageController.animateToPage(index,
                duration: const Duration(milliseconds: 150),
                curve: Curves.bounceInOut);
            setState(() {
              currentIndex = index;
            });
          },
          selectedItemColor: Colors.blueAccent,
          unselectedItemColor: Colors.black,
          items: [
            BottomNavigationBarItem(icon: Icon(Icons.home), label: "Home"),
            BottomNavigationBarItem(icon: Icon(Icons.dashboard), label: "Data"),
            BottomNavigationBarItem(
                icon: Icon(Icons.verified_user), label: "Repository"),
          ]),
    );
  }
}
