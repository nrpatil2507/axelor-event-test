/*
 * Axelor Business Solutions
 *
 * Copyright (C) 2005-2020 Axelor (<http://axelor.com>).
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.axelor.apps.message.db.repo;

import com.axelor.apps.message.db.TemplateContext;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class TemplateContextRepository extends JpaRepository<TemplateContext> {

  public TemplateContextRepository() {
    super(TemplateContext.class);
  }

  public TemplateContext findByName(String name) {
    return Query.of(TemplateContext.class)
        .filter("self.name = :name")
        .bind("name", name)
        .fetchOne();
  }
}
