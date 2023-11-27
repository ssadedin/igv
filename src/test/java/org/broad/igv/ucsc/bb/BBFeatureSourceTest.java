package org.broad.igv.ucsc.bb;

import htsjdk.tribble.Feature;
import org.broad.igv.feature.BasicFeature;
import org.broad.igv.feature.genome.Genome;
import org.broad.igv.util.TestUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class BBFeatureSourceTest {

    @Test
    public void testbed9_2 () throws IOException {
        String path = TestUtils.DATA_DIR + "bb/myBigBed2.bb";
        String chr = "chr7";
        int start = 0;
        int end = Integer.MAX_VALUE;

        Genome genome = null;
        BBFile reader = new BBFile(path, genome);
        BBFeatureSource bwSource = new BBFeatureSource(reader, genome);

        Iterator<BasicFeature> iter = bwSource.getFeatures(chr, start, end);

        List<BasicFeature> features = new ArrayList<>();
        while(iter.hasNext()) {
            features.add(iter.next());
        }

        assertEquals(3339, features.size());   // Verified in iPad app

        //chr7	773975	792642	uc003sjb.2	0	+	776710	791816	0,255,0	HEATR2	Q86Y56-3
        BasicFeature f = features.get(20);
        assertEquals(f.getStart(), 773975);
        assertEquals(f.getAttribute("geneSymbol"), "HEATR2");
        assertEquals(f.getAttribute("spID"), "Q86Y56-3");
    }

    @Test
    public void testChromAlias () throws IOException {
        String path = TestUtils.DATA_DIR + "bb/myBigBed2.bb";
        String chr = "7";
        int start = 0;
        int end = Integer.MAX_VALUE;

        Genome genome = TestUtils.mockNCBIGenome();
        BBFile reader =  new BBFile(path, genome);
        BBFeatureSource bwSource = new BBFeatureSource(reader, genome);

        Iterator<BasicFeature> iter = bwSource.getFeatures(chr, start, end);

        List<BasicFeature> features = new ArrayList<>();
        while(iter.hasNext()) {
            features.add(iter.next());
        }

        assertEquals(3339, features.size());   // Verified in iPad app

        //chr7	773975	792642	uc003sjb.2	0	+	776710	791816	0,255,0	HEATR2	Q86Y56-3
        BasicFeature f = features.get(20);
        assertEquals(f.getStart(), 773975);
        assertEquals(f.getAttribute("geneSymbol"), "HEATR2");
        assertEquals(f.getAttribute("spID"), "Q86Y56-3");
    }

    @Test
    public void testBigBed() throws IOException {

        String path = TestUtils.DATA_DIR + "bb/chr21.refseq.bb";
        BBFile bbReader = new BBFile(path, null);
        assertTrue(bbReader.type == BBFile.Type.BIGBED);

        BBFeatureSource bbSource = new BBFeatureSource(bbReader, null);

        String chr = "chr21";
        int start = 26490012;
        int end = 42182827;


       Iterator<BasicFeature> iter =  bbSource.getFeatures(chr, start, end);
        int count = 0;
        while (iter.hasNext()) {
            BasicFeature f = iter.next();
            assertEquals(chr, f.getChr());
            assertTrue(f.getStart() <= end && f.getEnd() >= start);
            count++;
        }
        assertEquals("Feature count", 225, count);

    }


    @Test
    public void testBigGenePred() throws IOException {

        String path = "https://hgdownload.soe.ucsc.edu/hubs/GCA/009/914/755/GCA_009914755.4/bbi/GCA_009914755.4_T2T-CHM13v2.0.catLiftOffGenesV1/catLiftOffGenesV1.bb";

        BBFile bbReader = new BBFile(path,null);
        BBFeatureSource bbSource = new BBFeatureSource(bbReader, null);

        String chr = "chr21";
        int start = 26490012;
        int end = 42182827;

        Iterator<BasicFeature> iter = bbSource.getFeatures(chr, start, end);

        int count = 0;
        while (iter.hasNext()) {
            Feature f = iter.next();
            assertEquals(chr, f.getChr());
            assertTrue(f.getStart() <= end && f.getEnd() >= start);
            count++;
        }
        assertTrue(count > 0);
    }

    @Test
    public void testBigNarrowpeak() throws IOException {

        String path = "https://www.encodeproject.org/files/ENCFF154CVA/@@download/ENCFF154CVA.bigBed";

        BBFile bbReader = new BBFile(path, null);
        BBFeatureSource bbSource = new BBFeatureSource(bbReader, null);

        String chr = "chr21";
        int start = 26490012;
        int end = 42182827;

        Iterator<BasicFeature> iter = bbSource.getFeatures(chr, start, end);

        int count = 0;
        while (iter.hasNext()) {
            Feature f = iter.next();
            assertEquals(chr, f.getChr());
            count++;
        }
        assertTrue(count > 0);
    }

    // NOTE:  bigInteract is not yet supported in IGV desktop, bigInteract is treated as a plain bed file.
    @Test
    public void testBigInteract() throws IOException {

        String path = "https://s3.amazonaws.com/igv.org.demo/interactExample3.inter.bb";

        BBFile bbReader = new BBFile(path, null);
        BBFeatureSource bbSource = new BBFeatureSource(bbReader, null);

        String chr = "chr3";
        int start = 63081504;
        int end = 64501215;

        Iterator<BasicFeature> iter = bbSource.getFeatures(chr, start, end);

        int count = 0;
        while (iter.hasNext()) {
            Feature f = iter.next();
            assertEquals(chr, f.getChr());
            count++;
        }
        assertTrue(count > 0);
    }


//    test("interact features", async function () {
//        const url = "test/data/bb/interactExample3.inter.bb"
//
//        const chr = "chr3"
//        const start = 63702628
//        const end = 63880091
//        const bwSource = new BWSource({url: url})
//
//        const trackType = await bwSource.trackType()
//        assert.equal(trackType, "interact")
//
//        const features = await bwSource.getFeatures({chr, start, end, bpPerPixel: 1})
//        assert.ok(features)
//        assert.equal(features.length, 18)
//
//        //chr3	63741418	63978511	.	350	6	.	0	chr3	63741418	63743120	.	.	chr3	63976338	63978511	.	.
//        const secondFeature = features[1]
//        assert.equal(secondFeature.start1, 63741418)
//        assert.equal(secondFeature.end1, 63743120)
//        assert.equal(secondFeature.start2, 63976338)
//        assert.equal(secondFeature.end2, 63978511)
//    })

    // NOTE:  bigInteract is not yet supported in IGV desktop, bigInteract is treated as a plain bed file.
    @Test
    public void testBigRmsk() throws IOException {

        String path = "https://hgdownload.soe.ucsc.edu/hubs/GCA/009/914/755/GCA_009914755.4/bbi/GCA_009914755.4_T2T-CHM13v2.0.t2tRepeatMasker/chm13v2.0_rmsk.bb";

        BBFile bbReader = new BBFile(path, null);
        BBFeatureSource bbSource = new BBFeatureSource(bbReader, null);

        String chr = "chr9";
        int start = 145481787;
        int end = 145482000;

        Iterator<BasicFeature> iter = bbSource.getFeatures(chr, start, end);

        int count = 0;
        while (iter.hasNext()) {
            Feature f = iter.next();
            assertEquals(chr, f.getChr());
            count++;
        }
        assertTrue(count > 0);
    }

    @Test
    public void testExtraIndexSearch() throws IOException {

        String path = TestUtils.DATA_DIR + "genomes/GCF_000002655.1.chromAlias.bb";
        BBFile bbReader = new BBFile(path, null);

        // There are 5 extra indexes, 1 for each alias
        String ncbiName = "3";
        BasicFeature f1 = (BasicFeature) bbReader.search(ncbiName);
        assertNotNull(f1);
        assertEquals(ncbiName, f1.getAttribute("ncbi"));

        String ucscName = "chr2";
        BasicFeature f2 = (BasicFeature) bbReader.search(ucscName);
        assertEquals(ucscName, f2.getAttribute("ucsc"));
    }


}