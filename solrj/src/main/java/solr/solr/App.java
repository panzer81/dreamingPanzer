package solr.solr;


import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SolrServerException, IOException 
    {
    	 String url = "http://localhost:8080/solr/employees";
    	
    	 SolrClient solr = new HttpSolrClient(url);
    	 
    	 SolrQuery query = new SolrQuery();

    	 query.setQuery("*:*");
    	 //query.setFacet(true);
    	 query.set("wt", "json");
    	 query.setRows(10);
    	 query.setStart(0);

    	 QueryResponse response = solr.query(query);
    	 
    	 solr.close();

    	 System.out.println(response);
    }
}
