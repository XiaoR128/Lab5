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
import MyException.Ipexception;
import MyException.Rulesexception;
import MyException.Samelabelexception;
import MyException.Vertexception;
import MyException.Weightrong;
import Strategy.InmethodStrategy;
import edge.Edge;
import edge.NetworkConnection;
import graph.Graph;
import graph.NetworkTopology;
import vertex.Vertex;

public class NetworkTopologyFactory extends GraphFactory{

	private static String name = SocialNetworkFactory.class.getName();      
	private static Logger log = Logger.getLogger(name);
	@Override
	public Graph<Vertex, Edge> createGraph(String filePath,InmethodStrategy in){
		int count=0;
		Graph<Vertex, Edge> graph = new NetworkTopology();
		int ff=0;
		try {
			//�����־
			ConsoleHandler consoleHandler = new ConsoleHandler();
			consoleHandler.setLevel(Level.INFO);
			log.addHandler(consoleHandler);
            
			Map<String, Vertex> map = new HashMap<>();
			int first =0;
			Set<Undirecom> set = new HashSet<>();
			Set<String> lili = new HashSet<>();
			
			long start = System.currentTimeMillis();
			String s = in.input(filePath);
			long end = System.currentTimeMillis();
			System.out.println("�����ļ���ʱ�䣺" + (end - start)+"ms");
			String[] ss = s.split("\n|\r");
			
			List<String> listname = new ArrayList<>();
			Pattern ppp=Pattern.compile("-[0-9]\\d*\\.*\\d*");
			Pattern ppp1=Pattern.compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
			Pattern pp=Pattern.compile("\\w+");
			Pattern p0=Pattern.compile("Vertex\\s*=.+");
			Pattern p00=Pattern.compile("Edge\\s*=.+");
			Pattern p000=Pattern.compile("HyperEdge\\s*=.+");
			Pattern p1=Pattern.compile("GraphName\\s*=.+");
			Pattern p2=Pattern.compile("Vertex\\s*=\\s*<\"\\w+\",\\s*\"(Computer|Router|Server|WirelessRouter)\",\\s*<\"((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))\">>");
			Pattern p3=
			Pattern.compile("Edge\\s*=\\s*<\"\\w+\",\\s*\"NetworkConnection\",\\s*\"[0-9]\\d*\\.*\\d*\",\\s*\"\\w+\",\\s*\"\\w+\",\\s*\"No\">");
//			GraphPoet graph = new GraphPoet();
			for (int i = 0; i < ss.length; i++) {
				String line = ss[i];
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
					List<String> li1 = new ArrayList<>();
					Matcher m1=p.matcher(line);
					while(m1.find()) {
						String b = m1.group(1);
						li1.add(b);
					}
					Matcher m2=p2.matcher(line);
					if(!m2.matches()) {
						if(li1.size()!=3) {
							handlevertnum();
						}else if(!li1.get(1).equals("Computer")
								&&!li1.get(1).equals("Router")
								&&!li1.get(1).equals("Server")) {
							handlevert();
						}else if(!pp.matcher(li1.get(0)).matches()){
							handlerules();
						}else if(!ppp1.matcher(li1.get(2)).matches()) {
							handleip();
						}
					}else {
						if(listname.contains(li1.get(0))) {
							handlesamela();
						}
						List<String> li = new ArrayList<>();
						Matcher m=p.matcher(line);
						while(m.find()) {
							String b = m.group(1);
							li.add(b);
						}
						switch(li.get(1)) {
						case "Computer":
							String[] mid = new String[1];
							mid[0] = li.get(2);
							listname.add(li.get(0));
							Vertex com = new ComputerVertexFactory().createVertex(li.get(0), "Computer", mid);
//							log.info("��ӵ�"+li.get(0));
							map.put(li.get(0), com);
							graph.addVertex(com);
							break;
						case "Router":
							String[] mid1 = new String[1];
							mid1[0] = li.get(2);
							listname.add(li.get(0));
							Vertex rou = new RouterVertexFactory().createVertex(li.get(0), "Router", mid1);
//							log.info("��ӵ�"+li.get(0));
							map.put(li.get(0), rou);
							graph.addVertex(rou);
							break;
						case "Server":
							String[] mid2 = new String[1];
							mid2[0] = li.get(2);
							listname.add(li.get(0));
							Vertex ser = new ServerVertexFactory().createVertex(li.get(0), "Server", mid2);
//							log.info("��ӵ�"+li.get(0));
							map.put(li.get(0), ser);
							graph.addVertex(ser);
							break;
						case "WirelessRouter":
							String[] mid3 = new String[1];
							mid3[0] = li.get(2);
							listname.add(li.get(0));
							Vertex wir = new WirelessRouterVertexFactory().createVertex(li.get(0), "WirelessRouter", mid3);
//							log.info("��ӵ�"+li.get(0));
							map.put(li.get(0), wir);
							graph.addVertex(wir);
							break;
						}
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
						}else if(!lis.get(1).equals("NetworkConnection")) {
							handerroredge();
						}else if(lis.get(lis.size()-1).equals("Yes")) {
							handledireandundire(graph,lis);
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
						if(first!=0) {
							int flag=0,ii=0;
//							for(Edge ed:graph.edges()) {
//								if(ed.getlabel().equals(lis.get(0))) {
//									count++;
//									ii++;
//								}
//								if((ed.vertices().get(0).getLabel().equals(lis.get(3)) || (ed.vertices().get(0).getLabel().equals(lis.get(4))))
//										&&(ed.vertices().get(1).getLabel().equals(lis.get(4))) || (ed.vertices().get(1).getLabel().equals(lis.get(3)))) {
//									flag++;
//								}
//							}
							if(lili.contains(lis.get(0))) {
								count++;
								i++;
							}
							Undirecom com = new Undirecom(lis.get(3), lis.get(4));
							if(set.contains(com)) {
								flag++;
							}
							if(flag!=1&&ii==0) {
								NetworkConnection net = new NetworkConnection(lis.get(0), Double.parseDouble(lis.get(2)));
								List<Vertex> list = new ArrayList<>();
								Vertex v5 = map.get(lis.get(3));
								Vertex v6 = map.get(lis.get(4));
								list.add(v5);
								list.add(v6);
								net.addVertices(list);
								
								set.add(com);
								lili.add(lis.get(0));
								
//								log.info("��ӱ�"+i);
								graph.addEdge(net);
							}else if(ii!=0) {
								log.info("Warning�����г������ظ���label������������������ÿһ��label���涼��һ");
								NetworkConnection net = new NetworkConnection(lis.get(0)+count, Double.parseDouble(lis.get(2)));
								List<Vertex> list = new ArrayList<>();
								Vertex v5 = map.get(lis.get(3));
								Vertex v6 = map.get(lis.get(4));
								list.add(v5);
								list.add(v6);
								net.addVertices(list);
								
								set.add(com);
								lili.add(lis.get(0));
								
//								log.info("��ӱ�"+i);
								graph.addEdge(net);
								}else if(flag==1) {
									log.info("Warning�����ر��г����˶��رߣ���������ֻ������һ���ߣ����������ı�");
								}
						}else {
							NetworkConnection net = new NetworkConnection(lis.get(0), Double.parseDouble(lis.get(2)));
							List<Vertex> list = new ArrayList<>();
							Vertex v5 = map.get(lis.get(3));
							Vertex v6 = map.get(lis.get(4));
							list.add(v5);
							list.add(v6);
							net.addVertices(list);
							
							Undirecom com = new Undirecom(lis.get(3), lis.get(4));
							set.add(com);
							lili.add(lis.get(0));
							
//							log.info("��ӱ�"+i);
							graph.addEdge(net);
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
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g = e.fixededgenum();
//			graph=g;
			ff=1;
		} catch (Erroredgeexception e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedit();
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
		} catch (Ipexception e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixededgenum();
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
		log.info("���󣺵�����Ͳ������ͼ,�����������¶�ȡ�ļ�");
		throw new Vertexception("Wrong:������Ͳ������ͼ");
	}
	
	public void handlevertnum() throws Vertexception{
		log.info("���󣺵���ȱ����Ϣ,�����������¶�ȡ�ļ�");
		throw new Vertexception("Wrong:there vertex loss some messege");
	}
	/**
	 * �������ȱ����Ϣ�����
	 * @throws Edgenumexception
	 */
	public void handleedgenum() throws Edgenumexception{
		log.info("���󣺱���ȱ����Ϣ,�����������¶�ȡ�ļ�");
		throw new Edgenumexception("Wrong:there edge loss some messege");
	}
	
	/**
	 * ������е�һЩ�ַ������Ϲ�������
	 * @throws Edgenumexception
	 */
	public void handlelastny() throws Edgenumexception{
		log.info("���󣺱ߵĺ������Ϣ�����Ϲ���,�����������¶�ȡ�ļ�");
		throw new Edgenumexception("Wrong:����ıߵ�������Ϣ�����Ϲ���");
	}
	
	/**
	 * �������û�д����ĵ�����
	 */
	public void handleednovert() throws Edgenumexception{
		log.info("����ͼ��û�������ĵ�,�����������¶�ȡ�ļ�");
		throw new Edgenumexception("Wrong:there graph has no such vertexs");
	}
	
	/**
	 * ����ߵ����������������͵����
	 * @throws Erroredgeexception
	 */
	public void handerroredge() throws Erroredgeexception{
		log.info("���󣺱ߵ����Ͳ���ȷ,�����������¶�ȡ�ļ�");
		throw new Erroredgeexception("Wrong:�ߵ����Ͳ���ȷ");
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
	
	public void handlesamela() throws Samelabelexception{
		log.info("���󣺵��label�ظ�,�����������¶�ȡ�ļ�");
		throw new Samelabelexception("Wrong:���label�ظ�");
	}
	/**
	 * ����label������������ʽ�����
	 * @throws Rulesexception
	 */
	public void handlerules() throws Rulesexception{
		log.info("����label������������ʽҪ��,�����������¶�ȡ�ļ�");
		throw new Rulesexception("label������������ʽҪ��");
	}
	
	public void handleweight() throws Weightrong{
		log.info("���󣺱ߵ�ȨֵΪ����,�����������¶�ȡ�ļ�");
		throw new Weightrong("ȨֵΪ���ˣ�����������");
	}
	
	public void handleip() throws Ipexception{
		log.info("���󣺵��е�ipֵ������,�����������¶�ȡ�ļ�");
		throw new Ipexception("�����IPֵ������");
	}
//	public static void main(String[] args) throws IOException {
//		GraphFactory g = new NetworkTopologyFactory();
//		Graph<Vertex, Edge> gra = g.createGraph("src/txt/NetworkTopology.txt");
//		System.out.println(gra.toString());
//	}
}


