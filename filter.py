import json
import sys
import gzip

BATCH_ID = sys.argv[1]
#TEST_LENGTH = 1000

print(f"processing BATCH_ID: {BATCH_ID}")

metaDict = {}

with gzip.open(f"S2ORC/20200705v1/full/metadata/metadata_{BATCH_ID}.jsonl.gz") as metaFile, open(f"S2ORC_screen/biology/metadata/metadata_{BATCH_ID}.jsonl", "w") as biologyFile, open(f"S2ORC_screen/chemistry/metadata/metadata_{BATCH_ID}.jsonl", "w") as chemistryFile, open(f"S2ORC_screen/medicine/metadata/metadata_{BATCH_ID}.jsonl", "w") as medicineFile:
    for line in metaFile:        
        paperDict = json.loads(line)
        if (
            (paperDict["mag_field_of_study"]) and 
                (("Medicine" in paperDict["mag_field_of_study"])
                or ("Biology" in paperDict["mag_field_of_study"])
                or ("Chemistry") in paperDict["mag_field_of_study"])
            ):
            if(paperDict["has_pdf_parse"] == True):
                metaDict[paperDict["paper_id"]] = paperDict
            
            if("Medicine" in paperDict["mag_field_of_study"]):
                json.dump(paperDict, medicineFile)
                medicineFile.write("\n")
            if("Biology" in paperDict["mag_field_of_study"]):
                json.dump(paperDict, biologyFile)
                biologyFile.write("\n")
            if("Chemistry" in paperDict["mag_field_of_study"]):
                json.dump(paperDict, chemistryFile)
                chemistryFile.write("\n")


with gzip.open(f"S2ORC/20200705v1/full/pdf_parses/pdf_parses_{BATCH_ID}.jsonl.gz") as pdfFile,  open(f"S2ORC_screen/biology/pdf_parses/pdf_parses_{BATCH_ID}.jsonl", "w") as biologyFile, open(f"S2ORC_screen/chemistry/pdf_parses/pdf_parses_{BATCH_ID}.jsonl", "w") as chemistryFile, open(f"S2ORC_screen/medicine/pdf_parses/pdf_parses_{BATCH_ID}.jsonl", "w") as medicineFile:
    for line in pdfFile:
        pdfDict = json.loads(line)
        if(pdfDict["paper_id"] in metaDict):
            paperDict = metaDict[pdfDict["paper_id"]]

            if("Medicine" in paperDict["mag_field_of_study"]):
                json.dump(pdfDict, medicineFile)
                medicineFile.write("\n")
            if("Biology" in paperDict["mag_field_of_study"]):
                json.dump(pdfDict, biologyFile)
                biologyFile.write("\n")
            if("Chemistry" in paperDict["mag_field_of_study"]):
                json.dump(pdfDict, chemistryFile)
                chemistryFile.write("\n")

