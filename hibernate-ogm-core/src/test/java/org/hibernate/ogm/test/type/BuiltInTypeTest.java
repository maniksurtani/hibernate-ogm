/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.hibernate.ogm.test.type;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.ogm.test.simpleentity.OgmTestCase;

/**
 * @author Emmanuel Bernard <emmanuel@hibernate.org>
 * @author Nicolas Helleringer
 */
public class BuiltInTypeTest extends OgmTestCase {

	@Test
	public void testTypesSupport() throws Exception {
		final Session session = openSession();

		Transaction transaction = session.beginTransaction();
		Bookmark b = new Bookmark();
		b.setId("42");
		b.setDescription( "Hibernate Site" );
		b.setUrl(new URL("http://www.hibernate.org/"));
		BigDecimal weigth = new BigDecimal("21.77");
		b.setSiteWeigth( weigth);
		BigInteger visitCount= new BigInteger( "444");
		b.setVisitCount(visitCount);
		b.setFavourite(true);
		Byte displayMask= new Byte((byte) '8');
		b.setDisplayMask(displayMask);
		Date now = new Date( System.currentTimeMillis() );
		b.setCreationDate( now );
		b.setDestructionDate( now );
		b.setUpdateDate( now );
		final Calendar iCal = Calendar.getInstance();
		iCal.setTimeInMillis( now.getTime() );
		b.setCreationCalendar( iCal );
		b.setDestructionCalendar( iCal );
		byte[] blob = new byte[5];
		blob[0] = '1';
		blob[1] = '2';
		blob[2] = '3';
		blob[3] = '4';
		blob[4] = '5';
		b.setBlob( blob );
		session.persist( b );
		transaction.commit();

		session.clear();

		transaction = session.beginTransaction();
		b = (Bookmark) session.get( Bookmark.class, b.getId() );
		assertEquals("http://www.hibernate.org/",b.getUrl().toString());
		assertEquals(weigth,b.getSiteWeigth());
		assertEquals(visitCount,b.getVisitCount());
		assertEquals(new Boolean(true),b.isFavourite());
		assertEquals(displayMask,b.getDisplayMask());
		session.delete( b );
		transaction.commit();

		session.close();
	}


	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class<?>[] {
				Bookmark.class
		};
	}
}
