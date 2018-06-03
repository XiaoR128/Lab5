package factory;

import java.io.BufferedReader;
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
import MyException.Rulesexception;
import MyException.Samelabelexception;
import MyException.Scoreexception;
import MyException.Vertexception;
import MyException.Weightrong;
import MyException.yearexception;
import edge.Edge;
import edge.MovieActorRelation;
import edge.MovieDirectorRelation;
import edge.NetworkConnection;
import edge.SameMovieHyperEdge;
import graph.Graph;
import graph.MovieGraph;
import vertex.Actor;
import vertex.Director;
import vertex.Movie;
import vertex.Vertex;

public class MovieGraphFactory extends GraphFactory{

	private static String name = SocialNetworkFactory.class.getName();      
	private static Logger log = Logger.getLogger(name);
	@Override
	public Graph<Vertex, Edge> createGraph(String filePath){
		FileReader file;
		int count=0;
		Graph<Vertex, Edge> graph = new MovieGraph();
		int ff=0;
		try {
			//�����־
			FileHandler fileHandler = new FileHandler("src/logger/logger4.txt"); 
            fileHandler.setLevel(Level.INFO); 
            fileHandler.setFormatter(new MyLogHander());
            log.addHandler(fileHandler); 
			
			file = new FileReader(filePath);
			BufferedReader bf = new BufferedReader(file);
			String line = null;
			List<String> listname = new ArrayList<>();
			Pattern ppp0=Pattern.compile("([0-9]|[0-9]\\.\\d{1,2}|10\\.*(0|00)*)");
			Pattern pppp=Pattern.compile("(19[0-9][0-9]|20[0-1][0-8])");
			Pattern ppp=Pattern.compile("-[0-9]\\d*\\.*\\d*");
			Pattern pp0=Pattern.compile("[0-9]\\d*\\.*\\d*");
			Pattern ppp1=Pattern.compile("[0-9]\\d*");
			Pattern pp=Pattern.compile("\\w+");
			Pattern p0=Pattern.compile("Vertex\\s*=.+");
			Pattern p00=Pattern.compile("Edge\\s*=.+");
			Pattern p000=Pattern.compile("HyperEdge\\s*=.+");
			Pattern p1=Pattern.compile("GraphName\\s*=.+");
			Pattern p2=Pattern.compile("Vertex\\s*=\\s*<\"\\w+\",\\s*\"(Actor|Director)\",\\s*<\"[0-9]\\d*\",\\s*\"(F|M)\">>");
			Pattern p22=Pattern.compile("Vertex\\s*=\\s*<\"\\w+\",\\s*\"Movie\",\\s*<\"(19[0-9][0-9]|20[0-1][0-8])\",\\s*\"\\w+\",\\s*\"([0-9]|[0-9]\\.\\d{1,2}|10\\.*(0|00)*)\">>");
			Pattern p3=
			Pattern.compile("Edge\\s*=\\s*<\"\\w+\",\\s*\"(MovieDirectorRelation|MovieActorRelation)\",\\s*\"-*[0-9]\\d*\\.*\\d*\",\\s*\"\\w+\",\\s*\"\\w+\",\\s*\"No\">");
//            Pattern p4=Pattern.compile("HyperEdge\\s*=\\s*<\"\\w+\",\\s*\"SameMovieHyperEdge\",\\s*.*>");
//			GraphPoet graph = new GraphPoet();
			while((line=bf.readLine())!=null) {
				if(line.isEmpty()) {
					continue;
				}
				Pattern p=Pattern.compile("\"(.*?)\"");
				if(p000.matcher(line).matches()) {
					List<String> li3 = new ArrayList<>();
					Matcher m3=p.matcher(line);
					while(m3.find()) {
						String b = m3.group(1);
						li3.add(b);
					}
					if(!pp.matcher(li3.get(0)).matches()) {
						handlerules();
					}else if(!li3.get(1).equals("SameMovieHyperEdge")) {
						handerroredge();
					}else if(li3.size()<=3) {
						handleedgenum();
					}
//					if(p4.matcher(line).matches()) {
					SameMovieHyperEdge same = new SameMovieHyperEdge(li3.get(0), 0);
					List<Vertex> list0 = new ArrayList<>();
					for (int j = 2; j < li3.size(); j++) {
						for (Vertex ver : graph.vertices()) {
							if (ver.getLabel().equals(li3.get(j))) {
								list0.add(ver);
							}
						}
					}
					same.addVertices(list0);
					log.info("��ӳ���"+li3.get(0));
					graph.addEdge(same);
				}
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
					List<String> li1 = new ArrayList<>();
					Matcher m1=p.matcher(line);
					while(m1.find()) {
						String b = m1.group(1);
						li1.add(b);
					}
					Matcher m2=p2.matcher(line);
					Matcher m22=p22.matcher(line);
					if(!m2.matches()&&!m22.matches()) {
						if(li1.get(1).equals("Movie")&&li1.size()!=5) {
							handlevertnum();
						}else if(!li1.get(1).equals("Movie")&&
								!li1.get(1).equals("Actor")&&
								!li1.get(1).equals("Director")) {
							handlevert();
						}else if(!pp.matcher(li1.get(0)).matches()){
							handlerules();
						}else if(li1.get(1).equals("Director")&&li1.size()!=4) {
							handlevertnum();
						}else if(li1.get(1).equals("Actor")&&li1.size()!=4) {
							handlevertnum();
						}else if(li1.get(1).equals("Movie")) {
							if(!pppp.matcher(li1.get(2)).matches()) {
								handleyear();
							}
							if(!ppp0.matcher(li1.get(li1.size()-1)).matches()) {
								handlescore();
							}
						}else if(li1.get(1).equals("Director")||li1.get(1).equals("Actor")) {
							if(!ppp1.matcher(li1.get(2)).matches()) {
								handlezhen();
							}
							if(!li1.get(2).equals("F")&&!li1.get(2).equals("M")) {
								handlegender();
							}
						}
					}else if(m2.matches()){
						if(listname.contains(li1.get(0))) {
							handlesamela();
						}
						switch(li1.get(1)) {
						case "Director":
							if(listname.contains(li1.get(0))) {
								handlesamela();
							}
							Director dir = new Director(li1.get(0));
							String[] mid2 = new String[2];
							for(int jj=0;jj<2;jj++) {
								mid2[jj] = li1.get(jj+2);
							}
							dir.fillVertexInfo(mid2);
							listname.add(li1.get(0));
							log.info("���Director��"+li1.get(0));
							graph.addVertex(dir);
							break;
						case "Actor":
							Actor act = new Actor(li1.get(0));
							String[] mid1 = new String[2];
							for(int j=0;j<2;j++) {
								mid1[j] = li1.get(j+2);
							}
							act.fillVertexInfo(mid1);
							listname.add(li1.get(0));
							log.info("���Actor��"+li1.get(0));
							graph.addVertex(act);
							break;
						}
					}else if(m22.matches()) {
						Movie mov = new Movie(li1.get(0));
						String[] mid = new String[3];
						for(int i=0;i<3;i++) {
							mid[i] = li1.get(i+2);
						}
						mov.fillVertexInfo(mid);
						listname.add(li1.get(0));
						log.info("���Movie��"+li1.get(0));
						graph.addVertex(mov);
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
						}else if(!lis.get(lis.size()-1).equals("No") && !lis.get(lis.size()-1).equals("Yes")) {
							handlelastny();
						}else if(!lis.get(1).equals("MovieActorRelation")&&
								!lis.get(1).equals("MovieDirectorRelation")) {
							handerroredge();
						}else if(lis.get(lis.size()-1).equals("Yes")) {
							handledireandundire(graph,lis);
						}else if(!pp.matcher(lis.get(0)).matches()) {
							handlerules();
						}
					}else {
						if(ppp.matcher(lis.get(2)).matches()&&
								lis.get(1).equals("MovieActorRelation")) {
							handleweight();
						}
						if(pp0.matcher(lis.get(2)).matches()&&
								lis.get(1).equals("MovieDirectorRelation")) {
							handlefuzhen();
						}
						if(lis.get(3).equals(lis.get(4))) {
							log.info("ͼ�������Ի���������������");
							continue;
						}
//						System.out.println(listname);
						if(!listname.contains(lis.get(3))||!listname.contains(lis.get(4))) {
							handleednovert();
						}
						if(!graph.edges().isEmpty()) {
							int flag=0,ii=0;
							for(Edge ed:graph.edges()) {
								if(ed.getlabel().equals(lis.get(0))) {
									count++;
									ii++;
								}
								if((ed.vertices().get(0).getLabel().equals(lis.get(3)) || (ed.vertices().get(0).getLabel().equals(lis.get(4))))
										&&(ed.vertices().get(1).getLabel().equals(lis.get(4))) || (ed.vertices().get(1).getLabel().equals(lis.get(3)))) {
									log.info("����ͼ�����˶��رߣ��Ѻ��Ժ�ߵı�");
									flag++;
								}
							}
							if(flag!=1&&ii==0) {
								switch(lis.get(1)) {
								case "MovieActorRelation":
//									System.out.println(1);
									MovieActorRelation movi = new MovieActorRelation(lis.get(0), Double.parseDouble(lis.get(2)));
									List<Vertex> list = new ArrayList<>();
									for(int j=3;j<5;j++) {
										for(Vertex ver : graph.vertices()) {
											if(ver.getLabel().equals(lis.get(j))) {
												list.add(ver);
											}
										}
									}
									movi.addVertices(list);
									log.info("��ӱ�"+lis.get(0));
									graph.addEdge(movi);
									break;
								case "MovieDirectorRelation":
									MovieDirectorRelation movd = new MovieDirectorRelation(lis.get(0),Double.parseDouble(lis.get(2)));
									List<Vertex> list1 = new ArrayList<>();
									for(int j=3;j<5;j++) {
										for(Vertex ver : graph.vertices()) {
											if(ver.getLabel().equals(lis.get(j))) {
												list1.add(ver);
											}
										}
									}
									movd.addVertices(list1);
									log.info("��ӱ�"+lis.get(0));
									graph.addEdge(movd);
									break;
								}
							}else if(ii!=0) {
								log.info("Warning�����г������ظ���label������������������ÿһ��label���涼��һ");
								switch(lis.get(1)) {
								case "MovieActorRelation":
									MovieActorRelation movi = new MovieActorRelation(lis.get(0)+count, Double.parseDouble(lis.get(2)));
									List<Vertex> list = new ArrayList<>();
									for(int j=3;j<5;j++) {
										for(Vertex ver : graph.vertices()) {
											if(ver.getLabel().equals(lis.get(j))) {
												list.add(ver);
											}
										}
									}
									movi.addVertices(list);
									log.info("��ӱ�"+lis.get(0));
									graph.addEdge(movi);
									break;
								case "MovieDirectorRelation":
									MovieDirectorRelation movd = new MovieDirectorRelation(lis.get(0)+count,Double.parseDouble(lis.get(2)));
									List<Vertex> list1 = new ArrayList<>();
									for(int j=3;j<5;j++) {
										for(Vertex ver : graph.vertices()) {
											if(ver.getLabel().equals(lis.get(j))) {
												list1.add(ver);
											}
										}
									}
									movd.addVertices(list1);
									log.info("��ӱ�"+lis.get(0));
									graph.addEdge(movd);
									break;
								}
								}
						}else {
							switch(lis.get(1)) {
							case "MovieActorRelation":
								MovieActorRelation movi = new MovieActorRelation(lis.get(0), Double.parseDouble(lis.get(2)));
								List<Vertex> list = new ArrayList<>();
								for(int j=3;j<5;j++) {
									for(Vertex ver : graph.vertices()) {
										if(ver.getLabel().equals(lis.get(j))) {
											list.add(ver);
										}
									}
								}
								movi.addVertices(list);
								log.info("��ӱ�"+lis.get(0));
								graph.addEdge(movi);
								break;
							case "MovieDirectorRelation":
								MovieDirectorRelation movd = new MovieDirectorRelation(lis.get(0),Double.parseDouble(lis.get(2)));
								List<Vertex> list1 = new ArrayList<>();
								for(int j=3;j<5;j++) {
									for(Vertex ver : graph.vertices()) {
										if(ver.getLabel().equals(lis.get(j))) {
											list1.add(ver);
										}
									}
								}
								movd.addVertices(list1);
								log.info("��ӱ�"+lis.get(0));
								graph.addEdge(movd);
								break;
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
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g = e.fixededgenum();
//			graph=g;
			ff=1;
		} catch (Erroredgeexception e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedit();
//			graph=g;
			ff=1;
		}catch (Rulesexception e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedrules();
//			graph=g;
			ff=1;
		} catch (Weightrong e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedweight();
//			graph=g;
			ff=1;
		} catch (yearexception e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedweight();
//			graph=g;
			ff=1;
		} catch (Scoreexception e) {
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
		log.info("Wrong:������Ͳ���ȷ");
		throw new Vertexception("Wrong:������Ͳ���ȷ");
	}
	
	public void handlevertnum() throws Vertexception{
		log.info("Wrong:��������һЩ��Ϣ");
		throw new Vertexception("Wrong:��������һЩ��Ϣ");
	}
	/**
	 * �������ȱ����Ϣ�����
	 * @throws Edgenumexception
	 */
	public void handleedgenum() throws Edgenumexception{
		log.info("Wrong:����ȱ����һЩ��Ϣ");
		throw new Edgenumexception("Wrong:����ȱ����һЩ��Ϣ");
	}
	
	/**
	 * ������е�һЩ�ַ������Ϲ�������
	 * @throws Edgenumexception
	 */
	public void handlelastny() throws Edgenumexception{
		log.info("Wrong:�����Ƿ�������ߵ��ַ������Ϲ���");
		throw new Edgenumexception("Wrong:the last messege is not \"No\"");
	}
	
	/**
	 * �������û�д����ĵ�����
	 */
	public void handleednovert() throws Edgenumexception{
		log.info("Wrong:ͼ��û�������ĵ�");
		throw new Edgenumexception("Wrong:there graph has no such vertexs");
	}
	
	/**
	 * ����ߵ����������������͵����
	 * @throws Erroredgeexception
	 */
	public void handerroredge() throws Erroredgeexception{
		log.info("Wrong:�ߵ����ʹ���");
		throw new Erroredgeexception("Wrong:�ߵ����ʹ���");
	}
	
	/**
	 * ��������ͼ�е�����ߵ����
	 * @throws Direandundire
	 */
	public void handledireandundire(Graph<Vertex, Edge> graph,List<String> lis){
		log.info("Warning������ͼ�����������,��������������߸ĳ������");
		NetworkConnection net = new NetworkConnection(lis.get(0), Double.parseDouble(lis.get(2)));
		List<Vertex> list = new ArrayList<>();
		for(int l=3;l<5;l++) {
			for(Vertex vert : graph.vertices()) {
				if(vert.getLabel().equals(lis.get(l))) {
					list.add(vert);
				}
			}
		}
		net.addVertices(list);
		graph.addEdge(net);
	}
	/**
	 * ����label������������ʽ�����
	 * @throws Rulesexception
	 */
	public void handlerules() throws Rulesexception{
		log.info("Wrong:label������������ʽ");
		throw new Rulesexception("Wrong:label������������ʽҪ��");
	}
	
	public void handleweight() throws Weightrong{
		log.info("Wrong:MovieActorRelation��ȨֵΪ����");
		throw new Weightrong("Wrong:MovieActorRelation��ȨֵΪ����");
	}
	
	public void handleyear() throws yearexception{
		log.info("Wrong:��ݵĸ�ʽ����");
		throw new yearexception("Wrong:��ݵĸ�ʽ����");
	}
	
	public void handlescore() throws Scoreexception{
		log.info("Wrong:���ֵĸ�ʽ����");
		throw new Scoreexception("Wrong:���ֵĸ�ʽ����");
	}
	
	public void handlezhen() throws Integerexception{
		log.info("Wrong:��������Բ�Ϊ����");
		throw new Integerexception("Wrong:��������Բ�Ϊ����");
	}
	
	public void handlegender() throws Genderexception{
		log.info("Wrong:��������Ե��Ա𲻷�");
		throw new Genderexception("Wrong:��������Ե��Ա𲻷�");
	}
	
	public void handlefuzhen() throws Genderexception{
		log.info("Wrong:MovieDirectorRelation�������������Ȩֵ");
		throw new Genderexception("Wrong:MovieDirectorRelation�������������Ȩֵ");
	}
	
	public void handlesamela() throws Samelabelexception{
		log.info("Wrong:���label�ظ�");
		throw new Samelabelexception("Wrong:���label�ظ�");
	}
//	public static void main(String[] args) throws IOException {
//		GraphFactory g = new MovieGraphFactory();
//		Graph<Vertex, Edge> gra = g.createGraph("src/txt/MovieGraph.txt");
//		System.out.println(gra.toString());
//	}
	
}