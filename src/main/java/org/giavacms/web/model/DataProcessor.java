package org.giavacms.web.model;

import org.giavacms.api.repository.Search;

import java.util.List;

/**
 * @param <T>
 * @author fiorenzo pizza
 */
public interface DataProcessor<T>
{

   public void process(List<T> list, Search<T> ricerca);

}
