/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ulti;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author The Shuyy
 */
public class Paging {

    public static List Pagination(int page, int pageSize, List list) {
        if (list == null || list.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return list.subList((page - 1) * pageSize, Math.min(page * pageSize, list.size()));
    }

}
