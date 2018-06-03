package factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
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
	public Graph<Vertex, Edge> createGraph(String filePath){
		FileReader file;
		int count=0;
		Graph<Vertex, Edge> graph = new SocialNetwork();
		int ff=0;
		try {
			File file1 = new File("src/logger/logger2.txt");
			file1.delete();
			file1.createNewFile();
			FileHandler fileHandler = new FileHandler("src/logger/logger2.txt"); 
            fileHandler.setLevel(Level.INFO); 
            fileHandler.setFormatter(new MyLogHander());
            log.addHandler(fileHandler); 
			file = new FileReader(filePath);
			BufferedReader bf = new BufferedReader(file);
			String line = null;
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
			while((line=bf.readLine())!=null) {
				if(line.isEmpty()) {
					continue;
				}
//				System.out.println(p3.matcher(line).matches());
				if(p000.matcher(line).matches()) {
					log.info("Warning����ͼ�г����˳��ߣ�������������");
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
						log.info("��ӵ�"+li.get(0));
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
							log.info("Warning����ͼ�г�����loop��������������");
							continue;
						}
						if(Double.parseDouble(lis.get(2))>=1) {
							handleoverweight();
						}
						if (!graph.edges().isEmpty()) {
							int ii = 0;
							for (Edge ed : graph.edges()) {
								if (ed.getlabel().equals(lis.get(0))) {
									count++;
									ii++;
								}
							}
							if(ii==0) {
								switch (lis.get(1)) {
								case "FriendTie":
									FriendTie fri = new FriendTie(lis.get(0), Double.parseDouble(lis.get(2)));
									List<Vertex> list = new ArrayList<>();
									for (int j = 3; j < 5; j++) {
										for (Vertex ver : graph.vertices()) {
											if (ver.getLabel().equals(lis.get(j))) {
												list.add(ver);
											}
										}
									}
									fri.addVertices(list);
									log.info("��ӱ�"+lis.get(0));
									graph.addEdge(fri);
									break;
								case "CommentTie":
									CommentTie com = new CommentTie(lis.get(0), Double.parseDouble(lis.get(2)));
									List<Vertex> list1 = new ArrayList<>();
									for (int j = 3; j < 5; j++) {
										for (Vertex ver : graph.vertices()) {
											if (ver.getLabel().equals(lis.get(j))) {
												list1.add(ver);
											}
										}
									}
									com.addVertices(list1);
									log.info("��ӱ�"+lis.get(0));
									graph.addEdge(com);
									break;
								case "ForwardTie":
									ForwardTie forw = new ForwardTie(lis.get(0), Double.parseDouble(lis.get(2)));
									List<Vertex> list2 = new ArrayList<>();
									for (int j = 3; j < 5; j++) {
										for (Vertex ver : graph.vertices()) {
											if (ver.getLabel().equals(lis.get(j))) {
												list2.add(ver);
											}
										}
									}
									forw.addVertices(list2);
									log.info("��ӱ�"+lis.get(0));
									graph.addEdge(forw);
								}
							}
							if (ii != 0) {
								log.info("Warning�����г������ظ���label������������������ÿһ��label���涼��һ");
								// Edge wordn = new WordNeighborhood(lis.get(0)+count,
								// Double.parseDouble(lis.get(2)));
								switch (lis.get(1)) {
								case "FriendTie":
									FriendTie fri = new FriendTie(lis.get(0) + count, Double.parseDouble(lis.get(2)));
									List<Vertex> list = new ArrayList<>();
									for (int j = 3; j < 5; j++) {
										for (Vertex ver : graph.vertices()) {
											if (ver.getLabel().equals(lis.get(j))) {
												list.add(ver);
											}
										}
									}
									fri.addVertices(list);
									log.info("��ӱ�"+lis.get(0));
									graph.addEdge(fri);
									break;
								case "CommentTie":
									CommentTie com = new CommentTie(lis.get(0) + count, Double.parseDouble(lis.get(2)));
									List<Vertex> list1 = new ArrayList<>();
									for (int j = 3; j < 5; j++) {
										for (Vertex ver : graph.vertices()) {
											if (ver.getLabel().equals(lis.get(j))) {
												list1.add(ver);
											}
										}
									}
									com.addVertices(list1);
									log.info("��ӱ�"+lis.get(0));
									graph.addEdge(com);
									break;
								case "ForwardTie":
									ForwardTie forw = new ForwardTie(lis.get(0) + count,
											Double.parseDouble(lis.get(2)));
									List<Vertex> list2 = new ArrayList<>();
									for (int j = 3; j < 5; j++) {
										for (Vertex ver : graph.vertices()) {
											if (ver.getLabel().equals(lis.get(j))) {
												list2.add(ver);
											}
										}
									}
									forw.addVertices(list2);
									log.info("��ӱ�"+lis.get(0));
									graph.addEdge(forw);
								}
							}
						} else {
							switch (lis.get(1)) {
							case "FriendTie":
								FriendTie fri = new FriendTie(lis.get(0), Double.parseDouble(lis.get(2)));
								List<Vertex> list = new ArrayList<>();
								for (int j = 3; j < 5; j++) {
									for (Vertex ver : graph.vertices()) {
										if (ver.getLabel().equals(lis.get(j))) {
											list.add(ver);
										}
									}
								}
								fri.addVertices(list);
								log.info("��ӱ�"+lis.get(0));
								graph.addEdge(fri);
								break;
							case "CommentTie":
								CommentTie com = new CommentTie(lis.get(0), Double.parseDouble(lis.get(2)));
								List<Vertex> list1 = new ArrayList<>();
								for (int j = 3; j < 5; j++) {
									for (Vertex ver : graph.vertices()) {
										if (ver.getLabel().equals(lis.get(j))) {
											list1.add(ver);
										}
									}
								}
								com.addVertices(list1);
								log.info("��ӱ�"+lis.get(0));
								graph.addEdge(com);
								break;
							case "ForwardTie":
								ForwardTie forw = new ForwardTie(lis.get(0), Double.parseDouble(lis.get(2)));
								List<Vertex> list2 = new ArrayList<>();
								for (int j = 3; j < 5; j++) {
									for (Vertex ver : graph.vertices()) {
										if (ver.getLabel().equals(lis.get(j))) {
											list2.add(ver);
										}
									}
								}
								forw.addVertices(list2);
								log.info("��ӱ�"+lis.get(0));
								graph.addEdge(forw);
							}
						}
					}
				}
			}
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Vertexception e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedvert();
//			graph=g;
			ff=1;
		} catch (Edgenumexception e) {
//			log.info("���󣺱ߵ����Ͳ��淶,�����������¶�ȡ�ļ�");
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g = e.fixededgenum();
//			graph=g;
			ff=1;
		} catch (Erroredgeexception e) {
//			log.info("���󣺱ߵ����Ͳ��淶,�����������¶�ȡ�ļ�");
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
	 * ��������ĺ���
	 * @param line
	 * @throws Vertexception
	 */
	public void handlevert() throws Vertexception{
		log.info("���󣺵�����Ͳ���Person,�����������¶�ȡ�ļ�");
		throw new Vertexception("Wrong:the Vertex type is not \"Person\"");
	}
	
	public void handlevertnum() throws Vertexception{
		log.info("����the vertex loss some messege");
		throw new Vertexception("Wrong:the vertex loss some messege");
	}
	/**
	 * �������ȱ����Ϣ�����
	 * @throws Edgenumexception
	 */
	public void handleedgenum() throws Edgenumexception{
		log.info("���󣺱���ȱ����Ϣ,�����������¶�ȡ�ļ�");
		throw new Edgenumexception("Wrong:the edge loss some messege");
	}
	
	/**
	 * ������е�һЩ�ַ������Ϲ�������
	 * @throws Edgenumexception
	 */
	public void handlelastny() throws Edgenumexception{
		log.info("���󣺱ߵ����Ͳ��淶,�����������¶�ȡ�ļ�");
		throw new Edgenumexception("Wrong:the last messege is not \"Yes\"");
	}
	
	/**
	 * �������û�д����ĵ�����
	 */
	public void handleednovert() throws Edgenumexception{
		log.info("����ͼ��û����������ĵ�,�����������¶�ȡ�ļ�");
		throw new Edgenumexception("Wrong:there graph has no such vertexs");
	}
	
	/**
	 * ����ߵ����������������͵����
	 * @throws Erroredgeexception
	 */
	public void handerroredge() throws Erroredgeexception{
		log.info("���󣺱ߵ��������벻��ȷ,�����������¶�ȡ�ļ�");
		throw new Erroredgeexception("Wrong:�ߵ��������벻��ȷ");
	}
	
	/**
	 * ��������ͼ�е�����ߵ����
	 * @throws Direandundire
	 */
	public void handledireandundire() throws Direandundire{
		log.info("������������ͼ�������������,�����������¶�ȡ�ļ�");
		throw new Direandundire("Wrong:��������ͼ�������������");
	}
	
	/**
	 * ����label������������ʽ�����
	 * @throws Rulesexception
	 */
	public void handlerules() throws Rulesexception{
		log.info("����label������������ʽҪ��,�����������¶�ȡ�ļ�");
		throw new Rulesexception("Wrong:label������������ʽҪ��");
	}
	
	public void handleweight() throws Weightrong{
		log.info("���󣺱���ȨֵΪ����,�����������¶�ȡ�ļ�");
		throw new Weightrong("Wrong:ȨֵΪ���ˣ�����������");
	}
	
	public void handleoverweight() throws Overweightexc{
		log.info("����Ȩֵ����1�ˣ�����������,�����������¶�ȡ�ļ�");
		throw new Overweightexc("Wrong:Ȩֵ����1�ˣ�����������");
	}
	
	public void handlezhen() throws Integerexception{
		log.info("��������ĵ�����Բ�Ϊ����,�����������¶�ȡ�ļ�");
		throw new Integerexception("Wrong:��������Բ�Ϊ����");
	}
	
	public void handlegender() throws Genderexception{
		log.info("��������ĵ�������Ա����,�����������¶�ȡ�ļ�");
		throw new Genderexception("Wrong:��������Ե��Ա𲻷�");
	}
	
	public void handlesamela() throws Samelabelexception{
		log.info("���󣺵��label�ظ�,�����������¶�ȡ�ļ�");
		throw new Samelabelexception("Wrong:���label�ظ�");
	}
//	public static void main(String[] args) throws IOException {
//		GraphFactory g = new SocialNetworkFactory();
//		Graph<Vertex, Edge> gra = g.createGraph("src/txt/SocialNetwork.txt");
//		System.out.println(gra.toString());
//	}
}
