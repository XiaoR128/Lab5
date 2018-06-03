package factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import MyException.Direandundire;
import MyException.Edgenumexception;
import MyException.Erroredgeexception;
import MyException.Genderexception;
import MyException.Integerexception;
import MyException.Overweightexc;
import MyException.Rulesexception;
import MyException.Samelabelexception;
import MyException.Vertexception;
import MyException.Weightrong;
import Strategy.InmethodStrategy;
import edge.CommentTie;
import edge.Edge;
import edge.ForwardTie;
import edge.FriendTie;
import graph.Graph;
import graph.SocialNetwork;
import vertex.Vertex;

public class SocialNetworkFactory extends GraphFactory{
	private static String name = SocialNetworkFactory.class.getName();      
	private static Logger log = Logger.getLogger(name);
	@Override
	public Graph<Vertex, Edge> createGraph(String filePath,InmethodStrategy in){
		int count=0;
		Graph<Vertex, Edge> graph = new SocialNetwork();
		int ff=0;
		try {
			ConsoleHandler consoleHandler = new ConsoleHandler();
			consoleHandler.setLevel(Level.INFO);
			log.addHandler(consoleHandler);
            
			Map<String, Vertex> map = new HashMap<>();
			int first =0;
			Set<String> lili = new HashSet<>();
			
			long start = System.currentTimeMillis();
			String s = in.input(filePath);
			long end = System.currentTimeMillis();
			System.out.println("读入文件的时间：" + (end - start)+"ms");
			String[] ss = s.split("\n|\r");
			
//			String line = null;
			List<String> listname = new ArrayList<>();
			Pattern ppp=Pattern.compile("-[0-9]\\d*\\.*\\d*");
			Pattern ppp1=Pattern.compile("[0-9]\\d*");
			Pattern pp=Pattern.compile("\\w+");
			Pattern p0000=Pattern.compile("(ForwardTie|CommentTie|FriendTie)");
			Pattern p0=Pattern.compile("Vertex\\s*=.+");
			Pattern p00=Pattern.compile("Edge\\s*=.+");
			Pattern p000=Pattern.compile("HyperEdge\\s*=.+");
			Pattern p1=Pattern.compile("GraphName\\s*=.+");
			Pattern p2=Pattern.compile("Vertex\\s*=\\s*<\"\\w+\",\\s*\"Person\",\\s*<\"(F|M)\",\\s*\"[0-9]\\d*\">>");
			Pattern p3=
			Pattern.compile("Edge\\s*=\\s*<\"\\w+\",\\s*\"(ForwardTie|CommentTie|FriendTie)\",\\s*\"[0-9]\\d*\\.*\\d*\",\\s*\"\\w+\",\\s*\"\\w+\",\\s*\"Yes\">");
//			GraphPoet graph = new GraphPoet();
			for (int i = 0; i < ss.length; i++) {
//				System.out.println(p3.matcher(line).matches());
				String line = ss[i];
				if(p000.matcher(line).matches()) {
					log.info("Warning：此图中出现了超边，处理方法：忽略");
					continue;
				}
				
				
				Pattern p=Pattern.compile("\"(.*?)\"");
				if(p1.matcher(line).matches()) {
					List<String> li2 = new ArrayList<>();
					Matcher m2=p.matcher(line);
					while(m2.find()) {
						String b = m2.group(1);
						li2.add(b);
					}
					if(!pp.matcher(li2.get(0)).matches()) {
						handlerules();
					}
				}
				if(p0.matcher(line).matches()) {
					Matcher m=p.matcher(line);
					List<String> li1 = new ArrayList<>();
					Matcher m1=p.matcher(line);
					while(m1.find()) {
						String b = m1.group(1);
						li1.add(b);
					}
					Matcher m2=p2.matcher(line);
					if(!m2.matches()) {
						
						if(li1.size()!=4) {
							handlevertnum();
						}else if(!li1.get(1).equals("Person")) {
							handlevert();
						}else if(!pp.matcher(li1.get(0)).matches()){
							handlerules();
						}else if(!ppp1.matcher(li1.get(3)).matches()) {
							handlezhen();
						}else if(!li1.get(2).equals("F")&&!li1.get(2).equals("M")) {
							handlegender();
						}
					}else {
						if(listname.contains(li1.get(0))) {
							handlesamela();
						}
						List<String> li = new ArrayList<>();
						while(m.find()) {
							String b = m.group(1);
							li.add(b);
						}
						String[] arg = new String[2];
						arg[0] = li.get(2);
						arg[1] = li.get(3);
						listname.add(li.get(0));
						Vertex per = new PersonVertexFactory().createVertex(li.get(0), "Person", arg);
						map.put(li.get(0), per);
//						log.info("添加点"+li.get(0));
						graph.addVertex(per);
					}
				}
				if(p00.matcher(line).matches()) {
					Matcher m=p.matcher(line);
					List<String> lis = new ArrayList<>();
					while(m.find()) {
						String b = m.group(1);
						lis.add(b);
					}
					Matcher m3=p3.matcher(line);
					if(!m3.matches()) {
						if(lis.size()!=6) {
							handleedgenum();
						}else if(!lis.get(lis.size()-1).equals("Yes") && !lis.get(lis.size()-1).equals("No")) {
							handlelastny();
						}else if(!p0000.matcher(lis.get(1)).matches()) {
							handerroredge();
						}else if(lis.get(lis.size()-1).equals("No")) {
							handledireandundire();
						}else if(!pp.matcher(lis.get(0)).matches()) {
							handlerules();
						}else if(ppp.matcher(lis.get(2)).matches()) {
							handleweight();
						}
					}else {
						if(!listname.contains(lis.get(3))||!listname.contains(lis.get(4))) {
							handleednovert();
						}
						if(lis.get(3).equals(lis.get(4))) {
							log.info("Warning：此图中出现了loop，处理方法：忽略");
							continue;
						}
						if(Double.parseDouble(lis.get(2))>=1) {
							handleoverweight();
						}
						if (first!=0) {
							int ii = 0;
//							for (Edge ed : graph.edges()) {
//								if (ed.getlabel().equals(lis.get(0))) {
//									count++;
//									ii++;
//								}
//							}
							if(lili.contains(lis.get(0))) {
								count++;
								i++;
							}
							if(ii==0) {
								switch (lis.get(1)) {
								case "FriendTie":
									FriendTie fri = new FriendTie(lis.get(0), Double.parseDouble(lis.get(2)));
									List<Vertex> list = new ArrayList<>();
									Vertex v5 = map.get(lis.get(3));
									Vertex v6 = map.get(lis.get(4));
									list.add(v5);
									list.add(v6);
									fri.addVertices(list);
									
									lili.add(lis.get(0));
									
//									log.info("添加边"+i);
									graph.addEdge(fri);
									break;
								case "CommentTie":
									CommentTie com = new CommentTie(lis.get(0), Double.parseDouble(lis.get(2)));
									List<Vertex> list1 = new ArrayList<>();
//									List<Vertex> list2 = new ArrayList<>();
									Vertex v3 = map.get(lis.get(3));
									Vertex v4 = map.get(lis.get(4));
									list1.add(v3);
									list1.add(v4);
									com.addVertices(list1);
									
									lili.add(lis.get(0));
									
//									log.info("添加边"+i);
									graph.addEdge(com);
									break;
								case "ForwardTie":
									ForwardTie forw = new ForwardTie(lis.get(0), Double.parseDouble(lis.get(2)));
//									List<Vertex> list2 = new ArrayList<>();
									List<Vertex> list2 = new ArrayList<>();
									Vertex v1 = map.get(lis.get(3));
									Vertex v2 = map.get(lis.get(4));
									list2.add(v1);
									list2.add(v2);
									forw.addVertices(list2);
									
									lili.add(lis.get(0));
									
//									log.info("添加边"+i);
									graph.addEdge(forw);
								}
							}
							if (ii != 0) {
								log.info("Warning：边中出现了重复的label，处理方法：将后续的每一个label后面都加一");
								// Edge wordn = new WordNeighborhood(lis.get(0)+count,
								// Double.parseDouble(lis.get(2)));
								switch (lis.get(1)) {
								case "FriendTie":
									FriendTie fri = new FriendTie(lis.get(0)+count, Double.parseDouble(lis.get(2)));
									List<Vertex> list = new ArrayList<>();
									Vertex v5 = map.get(lis.get(3));
									Vertex v6 = map.get(lis.get(4));
									list.add(v5);
									list.add(v6);
									fri.addVertices(list);
									
									lili.add(lis.get(0));
									
//									log.info("添加边"+i);
									graph.addEdge(fri);
									break;
								case "CommentTie":
									CommentTie com = new CommentTie(lis.get(0)+count, Double.parseDouble(lis.get(2)));
									List<Vertex> list1 = new ArrayList<>();
//									List<Vertex> list2 = new ArrayList<>();
									Vertex v3 = map.get(lis.get(3));
									Vertex v4 = map.get(lis.get(4));
									list1.add(v3);
									list1.add(v4);
									com.addVertices(list1);
									
									lili.add(lis.get(0));
									
//									log.info("添加边"+i);
									graph.addEdge(com);
									break;
								case "ForwardTie":
									ForwardTie forw = new ForwardTie(lis.get(0)+count, Double.parseDouble(lis.get(2)));
//									List<Vertex> list2 = new ArrayList<>();
									List<Vertex> list2 = new ArrayList<>();
									Vertex v1 = map.get(lis.get(3));
									Vertex v2 = map.get(lis.get(4));
									list2.add(v1);
									list2.add(v2);
									forw.addVertices(list2);
									
									lili.add(lis.get(0));
									
//									log.info("添加边"+i);
									graph.addEdge(forw);
								}
							}
						} else {
							switch (lis.get(1)) {
							case "FriendTie":
								FriendTie fri = new FriendTie(lis.get(0), Double.parseDouble(lis.get(2)));
								List<Vertex> list = new ArrayList<>();
								Vertex v5 = map.get(lis.get(3));
								Vertex v6 = map.get(lis.get(4));
								list.add(v5);
								list.add(v6);
								fri.addVertices(list);
								
								lili.add(lis.get(0));
								
//								log.info("添加边"+i);
								graph.addEdge(fri);
								break;
							case "CommentTie":
								CommentTie com = new CommentTie(lis.get(0), Double.parseDouble(lis.get(2)));
								List<Vertex> list1 = new ArrayList<>();
//								List<Vertex> list2 = new ArrayList<>();
								Vertex v3 = map.get(lis.get(3));
								Vertex v4 = map.get(lis.get(4));
								list1.add(v3);
								list1.add(v4);
								com.addVertices(list1);
								
								lili.add(lis.get(0));
								
//								log.info("添加边"+i);
								graph.addEdge(com);
								break;
							case "ForwardTie":
								ForwardTie forw = new ForwardTie(lis.get(0), Double.parseDouble(lis.get(2)));
//								List<Vertex> list2 = new ArrayList<>();
								List<Vertex> list2 = new ArrayList<>();
								Vertex v1 = map.get(lis.get(3));
								Vertex v2 = map.get(lis.get(4));
								list2.add(v1);
								list2.add(v2);
								forw.addVertices(list2);
								
								lili.add(lis.get(0));
								
//								log.info("添加边"+i);
								graph.addEdge(forw);
							}
							first++;
						}
					}
				}
			}
		}catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Vertexception e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedvert();
//			graph=g;
			ff=1;
		} catch (Edgenumexception e) {
//			log.info("错误：边的类型不规范,处理方法：重新读取文件");
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g = e.fixededgenum();
//			graph=g;
			ff=1;
		} catch (Erroredgeexception e) {
//			log.info("错误：边的类型不规范,处理方法：重新读取文件");
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedit();
//			graph=g;
			ff=1;
		} catch (Direandundire e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g = e.fixeddirandund();
//			graph=g;
			ff=1;
		} catch (Rulesexception e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedrules();
//			graph=g;
			ff=1;
		} catch (Weightrong e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedweight();
//			graph=g;
			ff=1;
		} catch (Overweightexc e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedweight();
//			graph=g;
			ff=1;
		} catch (Integerexception e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedit();
//			graph=g;
			ff=1;
		} catch (Genderexception e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedit();
//			graph=g;
			ff=1;
		} catch (Samelabelexception e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedit();
//			graph=g;
			ff=1;
		} 
		if(ff==1) {
			return null;
		}
		return graph;
	}
	
	/**
	 * 处理点错误的函数
	 * @param line
	 * @throws Vertexception
	 */
	public void handlevert() throws Vertexception{
		log.info("错误：点的类型不是Person,处理方法：重新读取文件");
		throw new Vertexception("Wrong:the Vertex type is not \"Person\"");
	}
	
	public void handlevertnum() throws Vertexception{
		log.info("错误：the vertex loss some messege");
		throw new Vertexception("Wrong:the vertex loss some messege");
	}
	/**
	 * 处理边中缺少信息的情况
	 * @throws Edgenumexception
	 */
	public void handleedgenum() throws Edgenumexception{
		log.info("错误：边中缺少信息,处理方法：重新读取文件");
		throw new Edgenumexception("Wrong:the edge loss some messege");
	}
	
	/**
	 * 处理边中的一些字符不符合规则的情况
	 * @throws Edgenumexception
	 */
	public void handlelastny() throws Edgenumexception{
		log.info("错误：边的类型不规范,处理方法：重新读取文件");
		throw new Edgenumexception("Wrong:the last messege is not \"Yes\"");
	}
	
	/**
	 * 处理边中没有创建的点的情况
	 */
	public void handleednovert() throws Edgenumexception{
		log.info("错误：图中没有这样定义的点,处理方法：重新读取文件");
		throw new Edgenumexception("Wrong:there graph has no such vertexs");
	}
	
	/**
	 * 处理边的类型是其他的类型的情况
	 * @throws Erroredgeexception
	 */
	public void handerroredge() throws Erroredgeexception{
		log.info("错误：边的类型输入不正确,处理方法：重新读取文件");
		throw new Erroredgeexception("Wrong:边的类型输入不正确");
	}
	
	/**
	 * 处理有向图中的无向边的情况
	 * @throws Direandundire
	 */
	public void handledireandundire() throws Direandundire{
		log.info("错误：这是有向图，不能有无向边,处理方法：重新读取文件");
		throw new Direandundire("Wrong:这是有向图，不能有无向边");
	}
	
	/**
	 * 处理label不满足正则表达式的情况
	 * @throws Rulesexception
	 */
	public void handlerules() throws Rulesexception{
		log.info("错误：label不满足正则表达式要求,处理方法：重新读取文件");
		throw new Rulesexception("Wrong:label不满足正则表达式要求");
	}
	
	public void handleweight() throws Weightrong{
		log.info("错误：边中权值为负了,处理方法：重新读取文件");
		throw new Weightrong("Wrong:权值为负了，不符合条件");
	}
	
	public void handleoverweight() throws Overweightexc{
		log.info("错误：权值超过1了，不满足条件,处理方法：重新读取文件");
		throw new Overweightexc("Wrong:权值超过1了，不符合条件");
	}
	
	public void handlezhen() throws Integerexception{
		log.info("错误：输入的点的属性不为整数,处理方法：重新读取文件");
		throw new Integerexception("Wrong:输入的属性不为整数");
	}
	
	public void handlegender() throws Genderexception{
		log.info("错误：输入的点的属性性别出错,处理方法：重新读取文件");
		throw new Genderexception("Wrong:输入的属性的性别不符");
	}
	
	public void handlesamela() throws Samelabelexception{
		log.info("错误：点的label重复,处理方法：重新读取文件");
		throw new Samelabelexception("Wrong:点的label重复");
	}
	
	
}
