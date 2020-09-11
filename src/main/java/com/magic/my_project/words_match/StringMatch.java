package com.magic.my_project.words_match;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文本搜索匹配, ,支持 部分 正则 如 . ? [ ] \ ( | ) ,不支持( )内再嵌套( )
 */
public class StringMatch extends BaseMatch {


    static {
        try {
            //SetKeywords(readWordFromFile("./src/main/resources/words.txt"));
        } catch (Exception e) {
            System.out.println("m敏感词初始化失败！");
            e.printStackTrace();
        }
    }

    /**
     * 增加敏感词
     *
     * @param path
     * @return
     */
    private static List<String> readWordFromFile(String path) {
        List<String> words;
        BufferedReader br = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path));
            //br = new BufferedReader(new InputStreamReader(WordFilter.class.getClassLoader().getResourceAsStream(path)));
            br = new BufferedReader(new InputStreamReader(fileInputStream));
            String readLine;
            words = new ArrayList<>(1200);
            while ((readLine = br.readLine()) != null) {
                if (StringUtils.isNotBlank(readLine)) {
                    words.add(readLine);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
            }
        }
        return words;
    }


    /**
     * 在文本中查找第一个关键字
     *
     * @param text 文本
     * @return
     */
    public String FindFirst(final String text) {
        TrieNode3 ptr = null;
        for (int i = 0; i < text.length(); i++) {
            final char t = text.charAt(i);

            TrieNode3 tn;
            if (ptr == null) {
                tn = _first[t];
            } else {
                if (ptr.HasKey(t) == false) {
                    if (ptr.HasWildcard) {
                        final String result = FindFirst(text, i + 1, ptr.WildcardNode);
                        if (result != null) {
                            return result;
                        }
                    }
                    tn = _first[t];
                } else {
                    tn = ptr.GetValue(t);
                }
            }
            if (tn != null) {
                if (tn.End) {
                    final int length = _keywordLength[tn.Results.get(0)];
                    final int s = i - length + 1;
                    if (s >= 0) {
                        return text.substring(s, i + 1);
                    }
                }
            }
            ptr = tn;
        }
        return null;
    }

    private String FindFirst(final String text, final int index, TrieNode3 ptr) {
        for (int i = index; i < text.length(); i++) {
            final char t = text.charAt(i);
            TrieNode3 tn;
            if (ptr.HasKey(t) == false) {
                if (ptr.HasWildcard) {
                    final String result = FindFirst(text, i + 1, ptr.WildcardNode);
                    if (result != null) {
                        return result;
                    }
                }
                return null;
            }
            tn = ptr.GetValue(t);

            if (tn.End) {
                final int length = _keywordLength[tn.Results.get(0)];
                final int s = i - length + 1;
                if (s >= 0) {
                    return text.substring(s, i + 1);
                }
            }
            ptr = tn;
        }
        return null;
    }

    /**
     * 在文本中查找所有的关键字
     *
     * @param text 文本
     * @return
     */
    public List<String> FindAll(final String text) {
        TrieNode3 ptr = null;
        final List<String> result = new ArrayList<String>();

        for (int i = 0; i < text.length(); i++) {
            final char t = text.charAt(i);
            TrieNode3 tn;
            if (ptr == null) {
                tn = _first[t];
            } else {
                if (ptr.HasKey(t) == false) {
                    if (ptr.HasWildcard) {
                        FindAll(text, i + 1, ptr.WildcardNode, result);
                    }
                    tn = _first[t];
                } else {
                    tn = ptr.GetValue(t);
                }
            }
            if (tn != null) {
                if (tn.End) {
                    for (final Integer item : tn.Results) {
                        final int length = _keywordLength[item];
                        final int s = i - length + 1;
                        if (s >= 0) {
                            final String key = text.substring(s, i + 1);
                            result.add(key);

                        }
                    }
                }
            }
            ptr = tn;
        }
        return result;
    }

    private void FindAll(final String text, final int index, TrieNode3 ptr, final List<String> result) {
        for (int i = index; i < text.length(); i++) {
            final char t = text.charAt(i);
            TrieNode3 tn;
            if (ptr.HasKey(t) == false) {
                if (ptr.HasWildcard) {
                    FindAll(text, i + 1, ptr.WildcardNode, result);
                }
                return;
            } else {
                tn = ptr.GetValue(t);
            }
            if (tn.End) {
                for (final Integer item : tn.Results) {
                    final int length = _keywordLength[item];
                    final int s = i - length + 1;
                    if (s >= 0) {
                        final String key = text.substring(s, i + 1);
                        result.add(key);
                    }
                }
            }
            ptr = tn;
        }
    }

    /**
     * 判断文本是否包含关键字
     *
     * @param text 文本
     * @return
     */
    public boolean ContainsAny(final String text) {
        TrieNode3 ptr = null;
        for (int i = 0; i < text.length(); i++) {
            final char t = text.charAt(i);
            TrieNode3 tn;
            if (ptr == null) {
                tn = _first[t];
            } else {
                if (ptr.HasKey(t) == false) {
                    if (ptr.HasWildcard) {
                        final boolean result = ContainsAny(text, i + 1, ptr.WildcardNode);
                        if (result) {
                            return true;
                        }
                    }
                    tn = _first[t];
                } else {
                    tn = ptr.GetValue(t);
                }
            }
            if (tn != null) {
                if (tn.End) {
                    final int length = _keywordLength[tn.Results.get(0)];
                    final int s = i - length + 1;
                    if (s >= 0) {
                        return true;
                    }
                }
            }
            ptr = tn;
        }
        return false;
    }

    private boolean ContainsAny(final String text, final int index, TrieNode3 ptr) {
        for (int i = index; i < text.length(); i++) {
            final char t = text.charAt(i);
            TrieNode3 tn;
            if (ptr.HasKey(t) == false) {
                if (ptr.HasWildcard) {
                    return ContainsAny(text, i + 1, ptr.WildcardNode);
                }
                return false;
            }
            tn = ptr.GetValue(t);

            if (tn.End) {
                final int length = _keywordLength[tn.Results.get(0)];
                final int s = i - length + 1;
                if (s >= 0) {
                    return true;
                }
            }
            ptr = tn;
        }
        return false;
    }

    /**
     * 在文本中替换所有的关键字, 替换符默认为 *
     *
     * @param text 文本
     * @return
     */
    public String Replace(final String text) {
        return Replace(text, '*');
    }

    /**
     * 在文本中替换所有的关键字
     *
     * @param text        文本
     * @param replaceChar 替换符
     * @return
     */
    public String Replace(final String text, final char replaceChar) {
        final StringBuilder result = new StringBuilder(text);

        TrieNode3 ptr = null;
        for (int i = 0; i < text.length(); i++) {
            final char t = text.charAt(i);
            TrieNode3 tn;
            if (ptr == null) {
                tn = _first[t];
            } else {
                if (ptr.HasKey(t) == false) {
                    if (ptr.HasWildcard) {
                        Replace(text, i + 1, ptr.WildcardNode, replaceChar, result);
                    }
                    tn = _first[t];
                } else {
                    tn = ptr.GetValue(t);
                }
            }
            if (tn != null) {
                if (tn.End) {
                    final int maxLength = _keywordLength[tn.Results.get(0)];
                    final int start = i + 1 - maxLength;
                    if (start >= 0) {
                        for (int j = start; j <= i; j++) {
                            result.setCharAt(j, replaceChar);
                        }
                    }
                }
            }
            ptr = tn;
        }
        return result.toString();
    }

    private void Replace(final String text, final int index, TrieNode3 ptr, final char replaceChar,
                         final StringBuilder result) {
        for (int i = index; i < text.length(); i++) {
            final char t = text.charAt(i);
            TrieNode3 tn;
            if (ptr.HasKey(t) == false) {
                if (ptr.HasWildcard) {
                    Replace(text, i + 1, ptr.WildcardNode, replaceChar, result);
                }
                return;
            }
            tn = ptr.GetValue(t);
            if (tn.End) {
                final int maxLength = _keywordLength[tn.Results.get(0)];
                final int start = i + 1 - maxLength;
                if (start >= 0) {
                    for (int j = start; j <= i; j++) {
                        result.setCharAt(j, replaceChar);
                    }
                }
            }
            ptr = tn;
        }
    }
}