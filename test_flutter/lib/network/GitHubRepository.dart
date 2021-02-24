import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:test_flutter/model/RepoItemInfo.dart';

class GitHubRepository {
  static GitHubRepository _mInstance;
  Dio _dio;

  GitHubRepository._internal() {
    _dio = Dio(BaseOptions(
        baseUrl: "https://api.github.com",
        connectTimeout: 5000,
        receiveTimeout: 5000,
        sendTimeout: 5000));
  }

  factory GitHubRepository.getInstance() => _getInstance();

  static _getInstance() {
    if (_mInstance == null) {
      _mInstance = GitHubRepository._internal();
    }
    return _mInstance;
  }

  getRepositories() async{
    var response = await _dio.get("/repositories",options: Options(headers: {"Accept":"application/vnd.github.v3+json"}));
    print("code:${response.statusCode}");
    print("data:${response.data.toString()}");
    Map<String, dynamic> decodeList = json.decode(response.data.toString());
    print("decodeList:$decodeList");
    var result = List<RepoItemInfo>();
    for(var item in decodeList.entries){
      result.add(RepoItemInfo.fromJson(item.value));
    }

    return result;
  }
}
