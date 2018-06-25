package com.asptttoulousenatation.core.media;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.asptttoulousenatation.core.loading.LoadingAlbumUi;
import com.asptttoulousenatation.core.loading.LoadingAlbumsUi;
import com.google.gdata.client.Query;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.Link;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.ServiceException;

@Path("/media")
@Produces("application/json")
public class MediaService {
	
	private static final Logger LOG = Logger.getLogger(MediaService.class.getSimpleName());
	
	@GET
	@Path("/photos")
	public LoadingAlbumsUi getAlbums(@QueryParam("limit") int limit) {
		LoadingAlbumsUi result = new LoadingAlbumsUi();
		try {
			PicasawebService myService = new PicasawebService("asptt_test");
			URL feedUrl = new URL(
					"https://picasaweb.google.com/data/feed/api/user/113747450706652808889?kind=album");

			Query myQuery = new Query(feedUrl);

			UserFeed searchResultsFeed = myService.query(myQuery,
					UserFeed.class);
			int maxAlbum = limit;
			boolean hasLimit = limit > 0;
			ListIterator<AlbumEntry> lEntryIt = searchResultsFeed
					.getAlbumEntries().listIterator();
			Set<String> excludedAlbum = new HashSet<String>();
			excludedAlbum.add("Profile Photos");
			excludedAlbum.add("Scrapbook Photos");
			excludedAlbum.add("5991796024554409745");//Photos actualités
			excludedAlbum.add("6023934185998314785"); //Article - site web
			
			while (lEntryIt.hasNext() || (lEntryIt.hasNext() && hasLimit && maxAlbum > 0)) {
				AlbumEntry adaptedEntry = lEntryIt.next();
				AlbumEntry lAlbum = (AlbumEntry) adaptedEntry;
				if (!excludedAlbum.contains(lAlbum.getTitle().getPlainText()) && !excludedAlbum.contains(lAlbum.getGphotoId())) {
					String feedHref = getLinkByRel(lAlbum.getLinks(),
							Link.Rel.FEED);
					AlbumFeed lAlbumEntries = myService.query(new Query(
							new URL(feedHref)), AlbumFeed.class);
					LoadingAlbumUi lAlbumUi = new LoadingAlbumUi(
							lAlbum.getGphotoId(), lAlbum.getTitle()
									.getPlainText(), lAlbumEntries
									.getPhotoEntries().get(0)
									.getMediaContents().get(0).getUrl());
					result.addAlbum(lAlbumUi);
					maxAlbum--;
				}
			}
		} catch (IOException | ServiceException e) {
			LOG.log(Level.SEVERE, "Erreur lors de la récupération des photos Google+", e);
		}
		return result;
	}
	
	/**
	 * Helper function to get a link by a rel value.
	 */
	public String getLinkByRel(List<Link> links, String relValue) {
		for (Link link : links) {
			if (relValue.equals(link.getRel())) {
				return link.getHref();
			}
		}
		throw new IllegalArgumentException("Missing " + relValue + " link.");
	}
}
