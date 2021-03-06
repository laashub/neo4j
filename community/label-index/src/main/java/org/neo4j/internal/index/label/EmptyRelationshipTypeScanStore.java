/*
 * Copyright (c) 2002-2020 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.internal.index.label;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;

import org.neo4j.annotations.documented.ReporterFactory;
import org.neo4j.collection.PrimitiveLongResourceCollections;
import org.neo4j.collection.PrimitiveLongResourceIterator;
import org.neo4j.common.EntityType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.internal.helpers.collection.Iterators;
import org.neo4j.io.pagecache.IOLimiter;
import org.neo4j.io.pagecache.tracing.cursor.PageCursorTracer;
import org.neo4j.kernel.api.index.IndexProgressor;
import org.neo4j.storageengine.api.EntityTokenUpdate;
import org.neo4j.storageengine.api.EntityTokenUpdateListener;

import static org.neo4j.common.EntityType.RELATIONSHIP;

public final class EmptyRelationshipTypeScanStore implements RelationshipTypeScanStore
{
    public static final RelationshipTypeScanStore INSTANCE = new EmptyRelationshipTypeScanStore();

    private EmptyRelationshipTypeScanStore()
    {
    }

    @Override
    public EntityType entityType()
    {
        return RELATIONSHIP;
    }

    @Override
    public TokenScanReader newReader()
    {
        return EmptyTokenScanReader.INSTANCE;
    }

    @Override
    public TokenScanWriter newWriter( PageCursorTracer cursorTracer )
    {
        return EmptyTokenScanWriter.INSTANCE;
    }

    @Override
    public TokenScanWriter newBulkAppendWriter( PageCursorTracer cursorTracer )
    {
        return EmptyTokenScanWriter.INSTANCE;
    }

    @Override
    public void force( IOLimiter limiter, PageCursorTracer cursorTracer )
    {   // no-op
    }

    @Override
    public AllEntriesTokenScanReader allEntityTokenRanges( PageCursorTracer cursorTracer )
    {
        return EmptyAllEntriesTokenScanReader.INSTANCE;
    }

    @Override
    public AllEntriesTokenScanReader allEntityTokenRanges( long fromEntityId, long toEntityId, PageCursorTracer cursorTracer  )
    {
        return EmptyAllEntriesTokenScanReader.INSTANCE;
    }

    @Override
    public ResourceIterator<File> snapshotStoreFiles()
    {
        return Iterators.emptyResourceIterator();
    }

    @Override
    public EntityTokenUpdateListener updateListener()
    {
        return EmptyEntityTokenUpdateListener.INSTANCE;
    }

    @Override
    public boolean isEmpty( PageCursorTracer cursorTracer )
    {
        return true;
    }

    @Override
    public void init()
    {   // no-op
    }

    @Override
    public void start()
    {   // no-op
    }

    @Override
    public void stop()
    {   // no-op
    }

    @Override
    public void shutdown()
    {   // no-op
    }

    @Override
    public void drop()
    {   // no-op
    }

    @Override
    public boolean isReadOnly()
    {
        return false;
    }

    @Override
    public boolean consistencyCheck( ReporterFactory reporterFactory, PageCursorTracer cursorTracer )
    {
        return true;
    }

    private static class EmptyEntityTokenUpdateListener implements EntityTokenUpdateListener
    {
        static final EntityTokenUpdateListener INSTANCE = new EmptyEntityTokenUpdateListener();

        @Override
        public void applyUpdates( Iterable<EntityTokenUpdate> labelUpdates, PageCursorTracer cursorTracer )
        {   // no-op
        }
    }

    private static class EmptyAllEntriesTokenScanReader implements AllEntriesTokenScanReader
    {
        static final AllEntriesTokenScanReader INSTANCE = new EmptyAllEntriesTokenScanReader();

        @Override
        public int rangeSize()
        {
            return 0;
        }

        @Override
        public long maxCount()
        {
            return 0;
        }

        @Override
        public void close() throws Exception
        {   // no-op
        }

        @Override
        public Iterator<EntityTokenRange> iterator()
        {
            return Collections.emptyIterator();
        }
    }

    private static class EmptyTokenScanWriter implements TokenScanWriter
    {
        static final TokenScanWriter INSTANCE = new EmptyTokenScanWriter();

        @Override
        public void write( EntityTokenUpdate update ) throws IOException
        {   // no-op
        }

        @Override
        public void close() throws IOException
        {   // no-op
        }
    }

    private static class EmptyTokenScanReader implements TokenScanReader
    {
        static final TokenScanReader INSTANCE = new EmptyTokenScanReader();

        @Override
        public PrimitiveLongResourceIterator entitiesWithToken( int tokenId, PageCursorTracer cursorTracer )
        {
            return PrimitiveLongResourceCollections.emptyIterator();
        }

        @Override
        public TokenScan entityTokenScan( int tokenId, PageCursorTracer cursorTracer )
        {
            return EmptyTokenScan.INSTANCE;
        }

        @Override
        public PrimitiveLongResourceIterator entitiesWithAnyOfTokens( long fromId, int[] tokenIds, PageCursorTracer cursorTracer )
        {
            return PrimitiveLongResourceCollections.emptyIterator();
        }
    }

    private static class EmptyTokenScan implements TokenScan
    {
        static final TokenScan INSTANCE = new EmptyTokenScan();

        @Override
        public IndexProgressor initialize( IndexProgressor.EntityTokenClient client, PageCursorTracer cursorTracer )
        {
            return IndexProgressor.EMPTY;
        }

        @Override
        public IndexProgressor initializeBatch( IndexProgressor.EntityTokenClient client, int sizeHint, PageCursorTracer cursorTracer )
        {
            return IndexProgressor.EMPTY;
        }
    }
}
